package dev.cammiescorner.camsbackpacks.common.items;

import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;

public class BackpackItem extends BlockItem
{
	public BackpackItem(BackpackBlock block)
	{
		super(block, new Settings().group(ItemGroup.TOOLS));
	}

	public DyeColor getColour()
	{
		return ((BackpackBlock) getBlock()).getColour();
	}
}
