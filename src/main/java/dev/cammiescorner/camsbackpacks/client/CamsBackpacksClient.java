package dev.cammiescorner.camsbackpacks.client;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.registry.ModScreenHandlers;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import static dev.cammiescorner.camsbackpacks.core.registry.ModBlocks.*;

public class CamsBackpacksClient implements ClientModInitializer {
    public static final ModelLayerLocation BACKPACK = new ModelLayerLocation(CamsBackpacks.id("backpack"), "main");
    public static boolean backpackScreenIsOpen = true;

    @Override
    public void onInitializeClient(ModContainer mod) {
        MenuScreens.register(ModScreenHandlers.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);

        EntityModelLayerRegistry.registerModelLayer(BACKPACK, BackpackModel::getTexturedModelData);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> BackpackHelper.dyeToDecimal(((BackpackBlock) state.getBlock()).getColour()), BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof BackpackBlock backpack && (backpack.getColour() != DyeColor.WHITE || backpack == WHITE_BACKPACK)).toArray(Block[]::new));
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> BackpackHelper.dyeToDecimal(((BackpackItem) stack.getItem()).getColour()), BuiltInRegistries.ITEM.stream().filter(item -> item instanceof BackpackItem backpack && (backpack.getColour() != DyeColor.WHITE || backpack == WHITE_BACKPACK.asItem())).toArray(Item[]::new));

        BlockRenderLayerMap.put(RenderType.cutout(), BuiltInRegistries.BLOCK.stream().filter(BackpackBlock.class::isInstance).toArray(Block[]::new));
    }
}
