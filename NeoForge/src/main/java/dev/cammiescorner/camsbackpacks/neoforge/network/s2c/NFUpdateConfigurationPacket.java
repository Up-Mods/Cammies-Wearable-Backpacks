package dev.cammiescorner.camsbackpacks.neoforge.network.s2c;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

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

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        logger.debug("Configuration received from server");
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handleMessage(this, ctx));
    }

    @OnlyIn(Dist.CLIENT)
    private static class ClientHandler {

        public static void handleMessage(NFUpdateConfigurationPacket msg, Supplier<NetworkEvent.Context> ctx) {
            UpdateConfigurationPacket.handle(msg.allowInventoryGui);
        }
    }
}
