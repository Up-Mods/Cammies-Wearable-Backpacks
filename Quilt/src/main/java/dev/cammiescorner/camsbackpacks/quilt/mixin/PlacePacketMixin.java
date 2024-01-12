package dev.cammiescorner.camsbackpacks.quilt.mixin;

import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QPlaceBackpackPacket;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlaceBackpackPacket.class)
public class PlacePacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void send(BlockHitResult hitResult) {
        QPlaceBackpackPacket.send(hitResult);
    }

}
