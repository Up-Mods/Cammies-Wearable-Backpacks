package dev.cammiescorner.camsbackpacks.quilt.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QEquipBackpackPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("equip_backpack");

    public static void send(boolean isBlockEntity, BlockPos pos) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeBoolean(isBlockEntity);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        boolean isBlockEntity = buf.readBoolean();

        server.execute(() -> EquipBackpackPacket.handle(player, pos, isBlockEntity));
    }
}
