package dev.cammiescorner.camsbackpacks.neoforge.mixin;

import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.neoforge.network.NetworkHandler;
import dev.cammiescorner.camsbackpacks.neoforge.network.s2c.NFUpdateConfigurationPacket;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(UpdateConfigurationPacket.class)
public class ConfigPacketMixin {

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void sendTo(Collection<ServerPlayer> players) {
        NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new NFUpdateConfigurationPacket(BackpacksConfig.allowInventoryGui));
    }

    /**
     * @author Up
     * @reason I'm too lazy to set up multi loader networking rn
     */
    @Overwrite
    public static void sendTo(ServerPlayer player) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new NFUpdateConfigurationPacket(BackpacksConfig.allowInventoryGui));
    }
}
