package dev.cammiescorner.camsbackpacks.core.mixin;

import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.util.BackpackHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"))
	private void camsbackpacks$dropInventory(CallbackInfo info) {
		ItemStack stack = getEquippedStack(EquipmentSlot.CHEST);
		BlockPos.Mutable pos = getBlockPos().mutableCopy();

		if(stack.getItem() instanceof BackpackItem item) {
			BlockState state = item.getBlock().getDefaultState().with(Properties.WATERLOGGED, false);

			while(!BackpackHelper.isReplaceable(world, pos.toImmutable()) && pos.getY() < world.getTopY() - 1)
				pos = pos.move(0, 1, 0);

			world.setBlockState(pos.toImmutable(), state);

			if(world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack)
				Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);

			equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
		}
	}
}
