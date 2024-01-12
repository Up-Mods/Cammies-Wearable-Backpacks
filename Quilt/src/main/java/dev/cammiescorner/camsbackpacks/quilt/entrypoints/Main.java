package dev.cammiescorner.camsbackpacks.quilt.entrypoints;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import dev.cammiescorner.camsbackpacks.quilt.compat.universalgraves.UniversalGravesCompat;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QEquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QOpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.quilt.network.c2s.QPlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;

import java.util.function.Supplier;

public class Main implements ModInitializer {

    private static final String UNIVERSAL_GRAVES_MOD_ID = "universal-graves";

    @Override
    public void onInitialize(ModContainer mod) {
        CamsBackpacks.init();

        ServerPlayNetworking.registerGlobalReceiver(QPlaceBackpackPacket.ID, QPlaceBackpackPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(QOpenBackpackScreenPacket.ID, QOpenBackpackScreenPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(QEquipBackpackPacket.ID, QEquipBackpackPacket::handle);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> Services.REGISTRY.getModBlocks().map(Supplier::get).forEach(content::accept));

        if (QuiltLoader.isModLoaded(UNIVERSAL_GRAVES_MOD_ID)) {
            UniversalGravesCompat.load();
        }

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> UpdateConfigurationPacket.sendTo(handler.player));
        ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(context -> {
            var server = context.server();
            if (server != null) {
                UpdateConfigurationPacket.sendTo(server.getPlayerList().getPlayers());
            }
        });
    }
}
