package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
	public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new BackpackScreenHandler(syncId, inventory, buf.readBlockPos(), buf.readBoolean()));

	public static void register() {
		Registry.register(Registry.SCREEN_HANDLER, CamsBackpacks.id("backpack"), BACKPACK_SCREEN_HANDLER);
	}
}
