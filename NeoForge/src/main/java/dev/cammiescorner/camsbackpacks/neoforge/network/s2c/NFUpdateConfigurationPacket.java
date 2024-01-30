package dev.cammiescorner.camsbackpacks.neoforge.network.s2c;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.NetworkEvent;
import org.slf4j.Logger;

public class NFUpdateConfigurationPacket {

    private static final Logger logger = LogUtils.getLogger();
    private final boolean allowInventoryGui;

    public NFUpdateConfigurationPacket(boolean allowInventoryGui) {
        this.allowInventoryGui = allowInventoryGui;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(allowInventoryGui);
    }

    public static NFUpdateConfigurationPacket decode(FriendlyByteBuf buf) {
        var allowInventoryGui = buf.readBoolean();
        return new NFUpdateConfigurationPacket(allowInventoryGui);
    }

    public void handle(NetworkEvent.Context ctx) {
        logger.debug("Configuration received from server");
        if(FMLEnvironment.dist.isClient()) {
            ClientHandler.handleMessage(this, ctx);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class ClientHandler {

        public static void handleMessage(NFUpdateConfigurationPacket msg, NetworkEvent.Context ctx) {
            UpdateConfigurationPacket.handle(msg.allowInventoryGui);
        }
    }
}
