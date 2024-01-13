package dev.cammiescorner.camsbackpacks.neoforge.mixin;

import dev.cammiescorner.camsbackpacks.neoforge.network.NetworkHandler;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFOpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(OpenBackpackScreenPacket.class)
public class OpenPacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void send() {
        NetworkHandler.INSTANCE.sendToServer(new NFOpenBackpackScreenPacket());
    }
}
