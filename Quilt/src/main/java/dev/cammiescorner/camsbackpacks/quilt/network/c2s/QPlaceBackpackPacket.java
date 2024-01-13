package dev.cammiescorner.camsbackpacks.quilt.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.BlockHitResult;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QPlaceBackpackPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("place_backpack");

    public static void send(BlockHitResult hitResult) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBlockHitResult(hitResult);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        BlockHitResult hitResult = buf.readBlockHitResult();

        server.execute(() -> PlaceBackpackPacket.handle(player, hitResult));
    }
}
