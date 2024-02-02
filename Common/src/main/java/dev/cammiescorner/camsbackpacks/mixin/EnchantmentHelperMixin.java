package dev.cammiescorner.camsbackpacks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @ModifyReturnValue(method = "getItemEnchantmentLevel", at = @At("RETURN"))
    private static int getItemEnchantmentLevelMixin(int original, Enchantment enchantment, ItemStack stack) {
        if (original == 0 && enchantment == Enchantments.BINDING_CURSE && stack.getItem() instanceof BackpackItem) {
            return 1;
        }

        return original;
    }
}
