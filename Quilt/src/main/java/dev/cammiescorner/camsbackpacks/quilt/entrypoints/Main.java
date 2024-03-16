package dev.cammiescorner.camsbackpacks.quilt.entrypoints;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import dev.cammiescorner.camsbackpacks.quilt.compat.universalgraves.UniversalGravesCompat;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.CustomPayloads;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Main implements ModInitializer {

    private static final String UNIVERSAL_GRAVES_MOD_ID = "universal-graves";

    @Override
    public void onInitialize(ModContainer mod) {
        CamsBackpacks.init();

        CustomPayloads.registerC2SPayload(PlaceBackpackPacket.ID, PlaceBackpackPacket::decode);
        CustomPayloads.registerC2SPayload(OpenBackpackScreenPacket.ID, OpenBackpackScreenPacket::decode);
        CustomPayloads.registerC2SPayload(EquipBackpackPacket.ID, EquipBackpackPacket::decode);

        ServerPlayNetworking.registerGlobalReceiver(PlaceBackpackPacket.ID, server(PlaceBackpackPacket::handle));
        ServerPlayNetworking.registerGlobalReceiver(OpenBackpackScreenPacket.ID, server(OpenBackpackScreenPacket::handle));
        ServerPlayNetworking.registerGlobalReceiver(EquipBackpackPacket.ID, server(EquipBackpackPacket::handle));

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

    public static <T extends CustomPacketPayload> ServerPlayNetworking.CustomChannelReceiver<T> server(BiConsumer<T, ServerPlayer> packetHandler) {
        return (server, player, handler, payload, responseSender) -> server.execute(() -> packetHandler.accept(payload, player));
    }
}
