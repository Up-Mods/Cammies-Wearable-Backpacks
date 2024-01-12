package dev.cammiescorner.camsbackpacks.quilt.mixin;

import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.s2c.QUpdateConfigurationPacket;
import net.minecraft.server.level.ServerPlayer;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(value = UpdateConfigurationPacket.class, remap = false)
public class ConfigPacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void sendTo(Collection<ServerPlayer> players) {
        QUpdateConfigurationPacket.sendTo(players);
    }

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void sendTo(ServerPlayer player) {
        QUpdateConfigurationPacket.sendTo(ServerPlayNetworking.getSender(player));
    }
}
