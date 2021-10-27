package dev.cammiescorner.camsbackpacks.core.mixin;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	@Shadow @Final private static TrackedData<ItemStack> STACK;

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
		ItemStack stack = getDataTracker().get(STACK);

		if(stack.getItem() instanceof BackpackItem && isOnGround()) {
			DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
			Inventories.fromTag(stack.getOrCreateTag(), inventory);
			stack.setTag(null);
			ItemScatterer.spawn(world, getBlockPos(), inventory);
			inventory.clear();
		}
	}
}
