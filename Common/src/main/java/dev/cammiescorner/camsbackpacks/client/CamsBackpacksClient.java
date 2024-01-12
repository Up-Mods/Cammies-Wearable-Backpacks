package dev.cammiescorner.camsbackpacks.client;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.init.ModScreenHandlers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class CamsBackpacksClient {
    public static final ModelLayerLocation BACKPACK = new ModelLayerLocation(CamsBackpacks.id("backpack"), "main");
    public static boolean backpackScreenIsOpen = true;
    public static boolean chestSlotUiEnabled = true;

    public static void init() {
        MenuScreens.register(ModScreenHandlers.BACKPACK_SCREEN_HANDLER.get(), BackpackScreen::new);
    }
}
