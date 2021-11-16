package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.client.screen.BackpackScreen;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow @Nullable public ClientPlayerEntity player;

	@Shadow public abstract void setScreen(@Nullable Screen screen);

	@Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target = "net/minecraft/client/MinecraftClient.setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 1))
	public void setScreen(MinecraftClient client, Screen screen) {
		if(player != null && !player.isCreative() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BackpackItem) {
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

			setScreen(new BackpackScreen(new BackpackScreenHandler(0, player.getInventory(), inventory, ScreenHandlerContext.create(player.world, player.getBlockPos()), false), player.getInventory(), stack.getName()));
		}
		else
			setScreen(screen);
	}
}
