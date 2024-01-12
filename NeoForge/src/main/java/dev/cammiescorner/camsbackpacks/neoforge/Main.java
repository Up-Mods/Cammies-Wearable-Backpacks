package dev.cammiescorner.camsbackpacks.neoforge;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.neoforge.services.NFRegistryHelper;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CamsBackpacks.MOD_ID)
public class Main {

    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        NFRegistryHelper.BLOCKS.register(bus);
        NFRegistryHelper.ITEMS.register(bus);
        NFRegistryHelper.BLOCK_ENTITY_TYPES.register(bus);
        NFRegistryHelper.MENU_TYPES.register(bus);
        bus.register(this);
        CamsBackpacks.init();
    }

    @SubscribeEvent
    public void onCreativeTabSetup(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            Services.REGISTRY.getModBlocks().forEach(event::accept);
        }
    }
}
