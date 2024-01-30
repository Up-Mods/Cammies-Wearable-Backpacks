package dev.cammiescorner.camsbackpacks.neoforge.entrypoints;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.init.ModBlocks;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import dev.cammiescorner.camsbackpacks.util.ColorHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = CamsBackpacks.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Client {

    public static void init() {

        //FIXME this is handled by resourcefulconfig in 1.20.4+
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, parent) -> new ConfigScreen(parent, null, CamsBackpacks.CONFIGURATOR.getConfig(BackpacksConfig.class))
                )
        );
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CamsBackpacksClient::init);
    }

    @SubscribeEvent
    public static void registerModelDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CamsBackpacksClient.BACKPACK, BackpackModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> ColorHelper.dyeToDecimal(((BackpackBlock) state.getBlock()).getColour()), BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof BackpackBlock backpack && (backpack.getColour() != DyeColor.WHITE || backpack == ModBlocks.WHITE_BACKPACK.get())).toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> ColorHelper.dyeToDecimal(((BackpackItem) stack.getItem()).getColour()), BuiltInRegistries.ITEM.stream().filter(item -> item instanceof BackpackItem backpack && (backpack.getColour() != DyeColor.WHITE || backpack == ModBlocks.WHITE_BACKPACK.get().asItem())).toArray(Item[]::new));
    }


}
