package dev.cammiescorner.camsbackpacks;

import dev.cammiescorner.camsbackpacks.common.network.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlocks;
import dev.cammiescorner.camsbackpacks.core.registry.ModScreenHandlers;
import dev.cammiescorner.camsbackpacks.core.util.EventHandler;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class CamsBackpacks implements ModInitializer {
	public static final String MOD_ID = "camsbackpacks";

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, BackpacksConfig.class);

		ServerPlayNetworking.registerGlobalReceiver(PlaceBackpackPacket.ID, PlaceBackpackPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.ID, OpenBackpackScreenPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(EquipBackpackPacket.ID, EquipBackpackPacket::handle);

		ModBlocks.register();
		ModBlockEntities.register();
		ModScreenHandlers.register();

		if(FabricLoader.getInstance().isModLoaded("universal-graves"))
			EventHandler.gravesEvents();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
