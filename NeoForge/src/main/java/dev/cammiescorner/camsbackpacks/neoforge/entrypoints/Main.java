package dev.cammiescorner.camsbackpacks.neoforge.entrypoints;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.neoforge.services.NFRegistryHelper;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@Mod(CamsBackpacks.MOD_ID)
public class Main {

    public Main(IEventBus bus) {
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
            Services.REGISTRY.getModBlocks().map(Supplier::get).forEach(event::accept);
        }
    }
}
