package dev.cammiescorner.camsbackpacks.client;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static dev.cammiescorner.camsbackpacks.core.registry.ModBlocks.*;

public class CamsBackpacksClient implements ClientModInitializer {
	public static final EntityModelLayer BACKPACK = new EntityModelLayer(CamsBackpacks.id("backpack"), "main");
	public static boolean backpackScreenIsOpen = true;

	@Override
	public void onInitializeClient() {
		//-----Screen Registry-----//
		ScreenRegistry.register(CamsBackpacks.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);

		//-----Entity Model Layers Registry-----//
		EntityModelLayerRegistry.registerModelLayer(BACKPACK, BackpackModel::getTexturedModelData);

		//-----Colour Registry-----//
		ColorProviderRegistryImpl.BLOCK.register((state, world, pos, tintIndex) -> BackpackHelper.dyeToDecimal(((BackpackBlock) state.getBlock()).getColour()),
				WHITE_BACKPACK, ORANGE_BACKPACK, MAGENTA_BACKPACK, LIGHT_BLUE_BACKPACK, YELLOW_BACKPACK, LIME_BACKPACK, PINK_BACKPACK,
				GRAY_BACKPACK, LIGHT_GRAY_BACKPACK, CYAN_BACKPACK, PURPLE_BACKPACK, BLUE_BACKPACK, BROWN_BACKPACK, GREEN_BACKPACK,
				RED_BACKPACK, BLACK_BACKPACK);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> BackpackHelper.dyeToDecimal(((BackpackItem) stack.getItem()).getColour()),
				WHITE_BACKPACK.asItem(), ORANGE_BACKPACK.asItem(), MAGENTA_BACKPACK.asItem(), LIGHT_BLUE_BACKPACK.asItem(),
				YELLOW_BACKPACK.asItem(), LIME_BACKPACK.asItem(), PINK_BACKPACK.asItem(), GRAY_BACKPACK.asItem(),
				LIGHT_GRAY_BACKPACK.asItem(), CYAN_BACKPACK.asItem(), PURPLE_BACKPACK.asItem(), BLUE_BACKPACK.asItem(),
				BROWN_BACKPACK.asItem(), GREEN_BACKPACK.asItem(), RED_BACKPACK.asItem(), BLACK_BACKPACK);

		//-----Block Layers Registry-----//
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), WHITE_BACKPACK, ORANGE_BACKPACK, MAGENTA_BACKPACK,
				LIGHT_BLUE_BACKPACK, YELLOW_BACKPACK, LIME_BACKPACK, PINK_BACKPACK, GRAY_BACKPACK, LIGHT_GRAY_BACKPACK, CYAN_BACKPACK,
				PURPLE_BACKPACK, BLUE_BACKPACK, BROWN_BACKPACK, GREEN_BACKPACK, RED_BACKPACK, BLACK_BACKPACK, GAY_BACKPACK,
				QPOC_BACKPACK, BI_BACKPACK, PAN_BACKPACK, TRANS_BACKPACK, GENDERQUEER_BACKPACK, GENDERFLUID_BACKPACK,
				ENBY_BACKPACK, ACE_BACKPACK, DEMI_BACKPACK, ARO_BACKPACK, AGENDER_BACKPACK);
	}
}
