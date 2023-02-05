package dev.cammiescorner.camsbackpacks.common.items;

import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DyeColor;

public class BackpackItem extends BlockItem {

	public BackpackItem(BackpackBlock block) {
		super(block, new Settings().maxCount(1));
	}

	public DyeColor getColour() {
		return ((BackpackBlock) getBlock()).getColour();
	}
}
