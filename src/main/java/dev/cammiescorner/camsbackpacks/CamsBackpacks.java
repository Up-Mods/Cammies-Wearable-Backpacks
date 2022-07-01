package dev.cammiescorner.camsbackpacks;

import dev.cammiescorner.camsbackpacks.common.network.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlocks;
import dev.cammiescorner.camsbackpacks.core.registry.ModScreenHandlers;
import dev.cammiescorner.camsbackpacks.core.util.EventHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class CamsBackpacks implements ModInitializer {
	public static final String MOD_ID = "camsbackpacks";
	public static BackpacksConfig config;

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PlaceBackpackPacket.ID, PlaceBackpackPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.ID, OpenBackpackScreenPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(EquipBackpackPacket.ID, EquipBackpackPacket::handle);

		ModBlocks.register();
		ModBlockEntities.register();
		ModScreenHandlers.register();

		if(FabricLoader.getInstance().isModLoaded("universal-graves"))
			EventHandler.gravesEvents();

		AutoConfig.register(BackpacksConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(BackpacksConfig.class).getConfig();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
