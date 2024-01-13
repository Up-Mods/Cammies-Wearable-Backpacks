package dev.cammiescorner.camsbackpacks.neoforge.mixin;

import dev.cammiescorner.camsbackpacks.neoforge.network.NetworkHandler;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFEquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
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
        NetworkHandler.INSTANCE.sendToServer(new NFEquipBackpackPacket(pos, isBlockEntity));
    }
}
