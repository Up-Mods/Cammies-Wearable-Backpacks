package dev.cammiescorner.camsbackpacks.common.items;

import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;

public class BackpackItem extends BlockItem {

    public BackpackItem(BackpackBlock block, Properties properties) {
        super(block, properties.stacksTo(1));
    }

    public DyeColor getColour() {
        return ((BackpackBlock) getBlock()).getColour();
    }
}
