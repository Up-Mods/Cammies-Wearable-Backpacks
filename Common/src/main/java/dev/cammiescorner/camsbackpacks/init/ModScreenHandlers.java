package dev.cammiescorner.camsbackpacks.init;

import dev.cammiescorner.camsbackpacks.menu.BackpackMenu;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import dev.cammiescorner.camsbackpacks.util.platform.service.RegistryHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModScreenHandlers {

    public static final Supplier<MenuType<BackpackMenu>> BACKPACK_SCREEN_HANDLER = create("backpack", () -> (syncId, inventory, buf) -> new BackpackMenu(syncId, inventory, buf.readBlockPos(), buf.readBoolean()));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> create(String name, Supplier<RegistryHelper.MenuExtendedFactory<T>> factory) {
        return Services.REGISTRY.registerMenu(name, factory);
    }

    public static void register() {
        // NO-OP
        // needed to load the class
    }
}
