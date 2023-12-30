package dev.cammiescorner.camsbackpacks.core.mixin.accessor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryMenu.class)
public interface InventoryMenuAccessor {
	@Accessor("TEXTURE_EMPTY_SLOTS")
	static ResourceLocation[] camsbackpacks$getEmptySlotsTextures() {
		throw new UnsupportedOperationException();
	}
}
