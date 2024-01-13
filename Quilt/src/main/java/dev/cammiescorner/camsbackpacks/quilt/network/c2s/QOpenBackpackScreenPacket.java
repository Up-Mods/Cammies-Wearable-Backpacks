package dev.cammiescorner.camsbackpacks.quilt.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QOpenBackpackScreenPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("open_backpack");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        server.execute(() -> OpenBackpackScreenPacket.handle(player));
    }
}
