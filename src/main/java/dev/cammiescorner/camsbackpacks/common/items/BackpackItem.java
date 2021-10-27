package dev.cammiescorner.camsbackpacks.common.items;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BackpackItem extends BlockItem {
	public BackpackItem(BackpackBlock block) {
		super(block, new Settings().group(ItemGroup.TOOLS));
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if(MinecraftClient.getInstance().options.keySneak.isPressed()) {
			tooltip.add(new TranslatableText("info." + CamsBackpacks.MOD_ID + ".instructions"));
		}
		else {
			tooltip.add(new TranslatableText("info." + CamsBackpacks.MOD_ID + ".more_info").formatted(Formatting.YELLOW));
		}
	}

	public DyeColor getColour() {
		return ((BackpackBlock) getBlock()).getColour();
	}
}
