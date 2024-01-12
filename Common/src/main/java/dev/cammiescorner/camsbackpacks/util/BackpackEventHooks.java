package dev.cammiescorner.camsbackpacks.util;

import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class BackpackEventHooks {

    public static void onDatapackReload(MinecraftServer server) {
        UpdateConfigurationPacket.sendTo(server.getPlayerList().getPlayers());
    }

    public static void onPlayerJoin(ServerPlayer player) {
        UpdateConfigurationPacket.sendTo(player);
    }
}
