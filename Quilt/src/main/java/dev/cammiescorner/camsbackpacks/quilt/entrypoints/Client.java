package dev.cammiescorner.camsbackpacks.quilt.entrypoints;

import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.init.ModBlocks;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import dev.cammiescorner.camsbackpacks.util.ColorHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.networking.api.CustomPayloads;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        CamsBackpacksClient.init();

        EntityModelLayerRegistry.registerModelLayer(CamsBackpacksClient.BACKPACK, BackpackModel::getTexturedModelData);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> ColorHelper.dyeToDecimal(((BackpackBlock) state.getBlock()).getColour()), BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof BackpackBlock backpack && (backpack.getColour() != DyeColor.WHITE || backpack == ModBlocks.WHITE_BACKPACK.get())).toArray(Block[]::new));
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ColorHelper.dyeToDecimal(((BackpackItem) stack.getItem()).getColour()), BuiltInRegistries.ITEM.stream().filter(item -> item instanceof BackpackItem backpack && (backpack.getColour() != DyeColor.WHITE || backpack == ModBlocks.WHITE_BACKPACK.get().asItem())).toArray(Item[]::new));

        BlockRenderLayerMap.put(RenderType.cutout(), BuiltInRegistries.BLOCK.stream().filter(BackpackBlock.class::isInstance).toArray(Block[]::new));

        CustomPayloads.registerS2CPayload(UpdateConfigurationPacket.ID, UpdateConfigurationPacket::decode);
        ClientPlayNetworking.registerGlobalReceiver(UpdateConfigurationPacket.ID, (ClientPlayNetworking.CustomChannelReceiver<UpdateConfigurationPacket>) (client, handler, payload, responseSender) -> client.execute(payload::handle));
    }
}
