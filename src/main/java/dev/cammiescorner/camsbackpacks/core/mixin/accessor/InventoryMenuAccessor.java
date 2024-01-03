package dev.cammiescorner.camsbackpacks.core.mixin.accessor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InventoryMenu.class)
public interface InventoryMenuAccessor {
	@Accessor("TEXTURE_EMPTY_SLOTS")
	static ResourceLocation[] camsbackpacks$getEmptySlotsTextures() {
		throw new UnsupportedOperationException();
	}

	@Invoker("onEquipItem")
	static void camsbackpacks$callOnEquipItem(Player player, EquipmentSlot slot, ItemStack newItem, ItemStack oldItem) {
		throw new UnsupportedOperationException();
	}
}
