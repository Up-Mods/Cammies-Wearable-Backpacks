package dev.cammiescorner.camsbackpacks.neoforge.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.c2s.EquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.network.c2s.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPlayPayloadHandler;
import net.neoforged.neoforge.network.registration.IDirectionAwarePayloadHandlerBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = CamsBackpacks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

    @SubscribeEvent
    public static void registerMessages(RegisterPayloadHandlerEvent event) {
        var registrar = event.registrar(CamsBackpacks.MOD_ID);

        registrar.play(EquipBackpackPacket.ID, EquipBackpackPacket::decode, server(EquipBackpackPacket::handle));
        registrar.play(OpenBackpackScreenPacket.ID, OpenBackpackScreenPacket::decode, server(OpenBackpackScreenPacket::handle));
        registrar.play(PlaceBackpackPacket.ID, PlaceBackpackPacket::decode, server(PlaceBackpackPacket::handle));

        registrar.play(UpdateConfigurationPacket.ID, UpdateConfigurationPacket::decode, handler -> handler.client((payload, context) -> context.workHandler().execute(payload::handle)));
    }

    private static <T extends CustomPacketPayload> Consumer<IDirectionAwarePayloadHandlerBuilder<T, IPlayPayloadHandler<T>>> server(BiConsumer<T, ServerPlayer> packetHandler) {
        return handler -> handler.server((payload, context) -> {
            if (context.player().orElse(null) instanceof ServerPlayer player) {
                context.workHandler().execute(() -> packetHandler.accept(payload, player));
            }
        });
    }
}
