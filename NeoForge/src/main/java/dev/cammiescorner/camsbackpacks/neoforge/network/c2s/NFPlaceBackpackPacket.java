package dev.cammiescorner.camsbackpacks.neoforge.network.c2s;

import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NFPlaceBackpackPacket {

    private final BlockHitResult target;

    public NFPlaceBackpackPacket(BlockHitResult target) {
        this.target = target;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockHitResult(target);
    }

    public static NFPlaceBackpackPacket decode(FriendlyByteBuf buf) {
        var target = buf.readBlockHitResult();
        return new NFPlaceBackpackPacket(target);
    }

    public void handle(NetworkEvent.Context ctx) {
        PlaceBackpackPacket.handle(ctx.getSender(), this.target);
    }
}
