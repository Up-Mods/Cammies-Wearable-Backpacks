package dev.cammiescorner.camsbackpacks.item;

import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class BackpackItem extends BlockItem {

    public BackpackItem(Block block, Properties properties) {
        super(block, properties.stacksTo(1));
    }

    public DyeColor getColour() {
        return ((BackpackBlock) getBlock()).getColour();
    }
}
