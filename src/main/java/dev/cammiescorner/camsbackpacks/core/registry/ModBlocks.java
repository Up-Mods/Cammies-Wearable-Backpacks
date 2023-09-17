package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ModBlocks {
	//-----Block Map-----//
	public static final LinkedHashMap<Block, Identifier> BLOCKS = new LinkedHashMap<>();

	//-----Blocks-----//
	public static final Block WHITE_BACKPACK = create("white_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block ORANGE_BACKPACK = create("orange_backpack", new BackpackBlock(DyeColor.ORANGE));
	public static final Block MAGENTA_BACKPACK = create("magenta_backpack", new BackpackBlock(DyeColor.MAGENTA));
	public static final Block LIGHT_BLUE_BACKPACK = create("light_blue_backpack", new BackpackBlock(DyeColor.LIGHT_BLUE));
	public static final Block YELLOW_BACKPACK = create("yellow_backpack", new BackpackBlock(DyeColor.YELLOW));
	public static final Block LIME_BACKPACK = create("lime_backpack", new BackpackBlock(DyeColor.LIME));
	public static final Block PINK_BACKPACK = create("pink_backpack", new BackpackBlock(DyeColor.PINK));
	public static final Block GRAY_BACKPACK = create("gray_backpack", new BackpackBlock(DyeColor.GRAY));
	public static final Block LIGHT_GRAY_BACKPACK = create("light_gray_backpack", new BackpackBlock(DyeColor.LIGHT_GRAY));
	public static final Block CYAN_BACKPACK = create("cyan_backpack", new BackpackBlock(DyeColor.CYAN));
	public static final Block PURPLE_BACKPACK = create("purple_backpack", new BackpackBlock(DyeColor.PURPLE));
	public static final Block BLUE_BACKPACK = create("blue_backpack", new BackpackBlock(DyeColor.BLUE));
	public static final Block BROWN_BACKPACK = create("brown_backpack", new BackpackBlock(DyeColor.BROWN));
	public static final Block GREEN_BACKPACK = create("green_backpack", new BackpackBlock(DyeColor.GREEN));
	public static final Block RED_BACKPACK = create("red_backpack", new BackpackBlock(DyeColor.RED));
	public static final Block BLACK_BACKPACK = create("black_backpack", new BackpackBlock(DyeColor.BLACK));
	public static final Block GAY_BACKPACK = create("gay_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block LESBIAN_BACKPACK = create("lesbian_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block QPOC_BACKPACK = create("qpoc_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block BI_BACKPACK = create("bi_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block PAN_BACKPACK = create("pan_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block TRANS_BACKPACK = create("trans_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block GENDERQUEER_BACKPACK = create("genderqueer_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block GENDERFLUID_BACKPACK = create("genderfluid_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block ENBY_BACKPACK = create("enby_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block ACE_BACKPACK = create("ace_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block DEMI_BACKPACK = create("demi_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block ARO_BACKPACK = create("aro_pride_backpack", new BackpackBlock(DyeColor.WHITE));
	public static final Block AGENDER_BACKPACK = create("agender_pride_backpack", new BackpackBlock(DyeColor.WHITE));

	//-----Registry-----//
	public static void register() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));

		for (Block block : BLOCKS.keySet()) {
			Registry.register(Registries.ITEM, BLOCKS.get(block), getItem((BackpackBlock) block));
			ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS_AND_UTILITIES).register(content -> content.addItem(block));
		}
	}

	private static BackpackItem getItem(BackpackBlock block) {
		return new BackpackItem(block);
	}

	private static <T extends Block> T create(String name, T block) {
		BLOCKS.put(block, CamsBackpacks.id(name));
		return block;
	}
}
