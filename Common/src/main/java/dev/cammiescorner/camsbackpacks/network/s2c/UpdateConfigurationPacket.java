package dev.cammiescorner.camsbackpacks.network.s2c;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

import java.util.Collection;

public record UpdateConfigurationPacket(boolean chestSlotUiEnabled) implements CustomPacketPayload {

    public static final ResourceLocation ID = CamsBackpacks.id("config");
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void sendTo(Collection<ServerPlayer> players) {
        players.forEach(UpdateConfigurationPacket::sendTo);
    }

    public static void sendTo(ServerPlayer player) {
        player.connection.send(new ClientboundCustomPayloadPacket(new UpdateConfigurationPacket(BackpacksConfig.allowInventoryGui)));
    }

    public void handle() {
        LOGGER.debug("Configuration received from server");
        CamsBackpacksClient.chestSlotUiEnabled = chestSlotUiEnabled();
        if (CamsBackpacksClient.chestSlotUiEnabled) {
            var minecraft = Minecraft.getInstance();
            CamsBackpacksClient.backpackScreenIsOpen = false;

            if (minecraft.screen instanceof BackpackScreen screen && !screen.getMenu().isBlockEntity && minecraft.player != null) {
                minecraft.setScreen(null);
                minecraft.player.sendSystemMessage(Component.translatable("error.camsbackpacks.chest_slot_ui_disabled"));
            }
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(chestSlotUiEnabled());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static UpdateConfigurationPacket decode(FriendlyByteBuf buf) {
        return new UpdateConfigurationPacket(buf.readBoolean());
    }
}
