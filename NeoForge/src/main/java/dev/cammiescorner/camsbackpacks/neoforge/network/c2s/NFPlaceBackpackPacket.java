package dev.cammiescorner.camsbackpacks.neoforge.network.c2s;

import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;

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

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        PlaceBackpackPacket.handle(ctx.get().getSender(), this.target);
    }
}
