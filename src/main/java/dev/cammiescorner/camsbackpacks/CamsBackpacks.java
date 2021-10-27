package dev.cammiescorner.camsbackpacks;

import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class CamsBackpacks implements ModInitializer {
	public static final String MOD_ID = "camsbackpacks";

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PlaceBackpackPacket.ID, PlaceBackpackPacket::handle);

		ModBlocks.register();
		ModBlockEntities.register();
	}
}
