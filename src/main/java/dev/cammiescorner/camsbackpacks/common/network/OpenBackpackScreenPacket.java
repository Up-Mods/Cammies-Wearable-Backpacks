package dev.cammiescorner.camsbackpacks.common.network;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class OpenBackpackScreenPacket {
	public static final Identifier ID = new Identifier(CamsBackpacks.MOD_ID, "open_backpack");

	public static void send() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		server.execute(() -> {
			final DefaultedList<ItemStack> stacks = DefaultedList.ofSize(36, ItemStack.EMPTY);
			ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
			NbtCompound tag = stack.getOrCreateNbt();
			Inventories.readNbt(tag, stacks);
			Inventory inventory = new Inventory() {
				@Override
				public void clear() {
					stacks.clear();
				}

				@Override
				public int size() {
					return stacks.size();
				}

				@Override
				public boolean isEmpty() {
					return stacks.isEmpty();
				}

				@Override
				public ItemStack getStack(int slot) {
					return stacks.get(slot);
				}

				@Override
				public ItemStack removeStack(int slot, int amount) {
					return Inventories.splitStack(stacks, slot, amount);
				}

				@Override
				public ItemStack removeStack(int slot) {
					return Inventories.removeStack(stacks, slot);
				}

				@Override
				public void setStack(int slot, ItemStack stack) {
					stacks.set(slot, stack);
				}

				@Override
				public void markDirty() {

				}

				@Override
				public boolean canPlayerUse(PlayerEntity player) {
					return true;
				}
			};

			player.openHandledScreen(new ExtendedScreenHandlerFactory() {
				@Override
				public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
					buf.writeBlockPos(player.getBlockPos());
					buf.writeBoolean(false);
				}

				@Override
				public Text getDisplayName() {
					return stack.getName();
				}

				@Override
				public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
					return new BackpackScreenHandler(syncId, playerInventory, inventory, ScreenHandlerContext.create(player.world, player.getBlockPos()), player.getBlockPos(), false);
				}
			});
		});
	}
}
