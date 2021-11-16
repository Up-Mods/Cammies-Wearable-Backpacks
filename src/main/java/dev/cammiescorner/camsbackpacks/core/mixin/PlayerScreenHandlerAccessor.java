package dev.cammiescorner.camsbackpacks.core.mixin;

import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerScreenHandler.class)
public interface PlayerScreenHandlerAccessor {
	@Accessor("EMPTY_ARMOR_SLOT_TEXTURES")
	static Identifier[] getEmptyArmorSlotTex() {
		throw new UnsupportedOperationException();
	}
}
