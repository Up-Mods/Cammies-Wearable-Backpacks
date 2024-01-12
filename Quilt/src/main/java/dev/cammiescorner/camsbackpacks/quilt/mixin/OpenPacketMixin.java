package dev.cammiescorner.camsbackpacks.quilt.mixin;

import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QOpenBackpackScreenPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = OpenBackpackScreenPacket.class, remap = false)
public class OpenPacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void send() {
        QOpenBackpackScreenPacket.send();
    }
}
