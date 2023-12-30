package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers {

    public static final MenuType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new BackpackScreenHandler(syncId, inventory, buf.readBlockPos(), buf.readBoolean()));

    public static void register() {
        Registry.register(BuiltInRegistries.MENU, CamsBackpacks.id("backpack"), BACKPACK_SCREEN_HANDLER);
    }
}
