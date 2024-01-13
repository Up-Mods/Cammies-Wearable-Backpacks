package dev.cammiescorner.camsbackpacks.quilt.network.s2c;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;

import java.util.Collection;

public class QUpdateConfigurationPacket {

    private static final Logger logger = LogUtils.getLogger();
    public static final ResourceLocation ID = CamsBackpacks.id("config");

    public static void sendTo(Collection<ServerPlayer> players) {
        ServerPlayNetworking.send(players, ID, encode());
    }

    public static void sendTo(PacketSender sender) {
        sender.sendPacket(ID, encode());
    }

    private static FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(BackpacksConfig.allowInventoryGui);

        return buf;
    }

    @ClientOnly
    public static void handle(Minecraft minecraft, ClientPacketListener packetListener, FriendlyByteBuf buf, PacketSender packetSender) {
        logger.debug("Configuration received from server");
        var allowInvGui = buf.readBoolean();
        minecraft.execute(() -> UpdateConfigurationPacket.handle(allowInvGui));
    }
}
