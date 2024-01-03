package dev.cammiescorner.camsbackpacks.common.network;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;

import java.util.Collection;

public class UpdateConfigurationPacket {

    private static final Logger logger = LogUtils.getLogger();
    public static final ResourceLocation ID = CamsBackpacks.id("config");

    public static void sendTo(Collection<ServerPlayer> players) {
        ServerPlayNetworking.send(players, ID, encode());
    }

    public static void sendTo(PacketSender sender) {
        sender.sendPacket(ID, encode());
    }

    private static FriendlyByteBuf encode() {
        var buf = PacketByteBufs.create();
        buf.writeBoolean(BackpacksConfig.allowInventoryGui);

        return buf;
    }

    @ClientOnly
    public static void handle(Minecraft minecraft, ClientPacketListener packetListener, FriendlyByteBuf buf, PacketSender packetSender) {
        logger.debug("Configuration received from server");
        var allowInvGui = buf.readBoolean();
        minecraft.execute(() -> {
            CamsBackpacksClient.chestSlotUiEnabled = allowInvGui;
            if(CamsBackpacksClient.chestSlotUiEnabled) {
                CamsBackpacksClient.backpackScreenIsOpen = false;

                if(minecraft.screen instanceof BackpackScreen screen && !screen.getMenu().isBlockEntity) {
                    minecraft.setScreen(null);
                    minecraft.player.sendSystemMessage(Component.translatable("error.camsbackpacks.chest_slot_ui_disabled"));
                }
            }
        });
    }
}
