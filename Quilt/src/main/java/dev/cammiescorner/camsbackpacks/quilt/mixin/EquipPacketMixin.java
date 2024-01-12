package dev.cammiescorner.camsbackpacks.quilt.mixin;

import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QEquipBackpackPacket;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EquipBackpackPacket.class)
public class EquipPacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void send(boolean isBlockEntity, BlockPos pos) {
        QEquipBackpackPacket.send(isBlockEntity, pos);
    }
}
