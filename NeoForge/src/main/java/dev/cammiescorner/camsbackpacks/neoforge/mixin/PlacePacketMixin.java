package dev.cammiescorner.camsbackpacks.neoforge.mixin;

import dev.cammiescorner.camsbackpacks.neoforge.network.NetworkHandler;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFPlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PlaceBackpackPacket.class, remap = false)
public class PlacePacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void send(BlockHitResult hitResult) {
        NetworkHandler.INSTANCE.sendToServer(new NFPlaceBackpackPacket(hitResult));
    }
}
