package dev.cammiescorner.camsbackpacks.quilt.network.c2s;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.menu.BackpackMenu;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QOpenBackpackScreenPacket {
    public static final ResourceLocation ID = CamsBackpacks.id("open_backpack");

    public static void send() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl network, FriendlyByteBuf buf, PacketSender sender) {
        server.execute(() -> {
            if(!BackpacksConfig.allowInventoryGui) {
                player.sendSystemMessage(Component.translatable("error.camsbackpacks.chest_slot_ui_disabled"), true);
                return;
            }

            final NonNullList<ItemStack> stacks = NonNullList.withSize(36, ItemStack.EMPTY);
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            CompoundTag tag = stack.getOrCreateTag();
            ContainerHelper.loadAllItems(tag, stacks);
            Container inventory = new Container() {
                @Override
                public void clearContent() {
                    stacks.clear();
                }

                @Override
                public int getContainerSize() {
                    return stacks.size();
                }

                @Override
                public boolean isEmpty() {
                    return stacks.isEmpty();
                }

                @Override
                public ItemStack getItem(int slot) {
                    return stacks.get(slot);
                }

                @Override
                public ItemStack removeItem(int slot, int amount) {
                    return ContainerHelper.removeItem(stacks, slot, amount);
                }

                @Override
                public ItemStack removeItemNoUpdate(int slot) {
                    return ContainerHelper.takeItem(stacks, slot);
                }

                @Override
                public void setItem(int slot, ItemStack stack) {
                    stacks.set(slot, stack);
                }

                @Override
                public void setChanged() {

                }

                @Override
                public boolean stillValid(Player player) {
                    return true;
                }
            };

            player.openMenu(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                    buf.writeBlockPos(player.blockPosition());
                    buf.writeBoolean(false);
                }

                @Override
                public Component getDisplayName() {
                    return stack.getHoverName();
                }

                @Override
                public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                    return new BackpackMenu(syncId, playerInventory, inventory, ContainerLevelAccess.create(player.level(), player.blockPosition()), player.blockPosition(), false);
                }
            });
        });
    }
}
