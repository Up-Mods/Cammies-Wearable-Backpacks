package dev.cammiescorner.camsbackpacks.network.s2c;

import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class UpdateConfigurationPacket {

    public static void sendTo(Collection<ServerPlayer> players) {
        throw new UnsupportedOperationException();
    }

    public static void sendTo(ServerPlayer player) {
        throw new UnsupportedOperationException();
    }

    public static void handle(boolean allowInventoryGui) {
        CamsBackpacksClient.chestSlotUiEnabled = allowInventoryGui;
        if (CamsBackpacksClient.chestSlotUiEnabled) {
            var minecraft = Minecraft.getInstance();
            CamsBackpacksClient.backpackScreenIsOpen = false;

            if (minecraft.screen instanceof BackpackScreen screen && !screen.getMenu().isBlockEntity && minecraft.player != null) {
                minecraft.setScreen(null);
                minecraft.player.sendSystemMessage(Component.translatable("error.camsbackpacks.chest_slot_ui_disabled"));
            }
        }
    }
}
