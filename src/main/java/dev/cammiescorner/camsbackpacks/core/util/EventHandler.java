package dev.cammiescorner.camsbackpacks.core.util;

import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EventHandler {
	public static void commonEvents() {
		ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
			ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
			World world = player.world;
			BlockPos.Mutable pos = player.getBlockPos().mutableCopy();

			if(stack.getItem() instanceof BackpackItem item) {
				BlockState state = item.getBlock().getDefaultState().with(Properties.WATERLOGGED, false);

				while(!BackpackHelper.isReplaceable(world, pos.toImmutable()) && pos.getY() < world.getTopY() - 1)
					pos = pos.move(0, 1, 0);

				world.setBlockState(pos.toImmutable(), state);

				if(world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack)
					Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);

				player.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
			}

			return true;
		});
	}
}
