package dev.cammiescorner.camsbackpacks.core.mixin.client;

import com.mojang.blaze3d.platform.InputUtil;
import net.minecraft.client.option.KeyBind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBind.class)
public interface KeyBindingAccessor {
	@Accessor("boundKey") InputUtil.Key getKey();
}
