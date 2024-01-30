package dev.cammiescorner.camsbackpacks.neoforge.network.c2s;

import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class NFEquipBackpackPacket {

    private final BlockPos pos;
    private final boolean isBlockEntity;

    public NFEquipBackpackPacket(BlockPos pos, boolean isBlockEntity) {
        this.pos = pos;
        this.isBlockEntity = isBlockEntity;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(isBlockEntity);
    }

    public static NFEquipBackpackPacket decode(FriendlyByteBuf buf) {
        var pos = buf.readBlockPos();
        var isBlockEntity = buf.readBoolean();
        return new NFEquipBackpackPacket(pos, isBlockEntity);
    }

    public void handle(NetworkEvent.Context ctx) {
        EquipBackpackPacket.handle(ctx.getSender(), this.pos, this.isBlockEntity);
    }
}
