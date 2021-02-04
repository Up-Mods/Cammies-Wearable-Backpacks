package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModBlocks
{
	//-----Block Map-----//
	public static final LinkedHashMap<Block, Identifier> BLOCKS = new LinkedHashMap<>();

	//-----Blocks-----//
	public static final Block WHITE_BACKPACK = create("white_backpack", new BackpackBlock(DyeColor.WHITE, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block ORANGE_BACKPACK = create("orange_backpack", new BackpackBlock(DyeColor.ORANGE, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block MAGENTA_BACKPACK = create("magenta_backpack", new BackpackBlock(DyeColor.MAGENTA, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block LIGHT_BLUE_BACKPACK = create("light_blue_backpack", new BackpackBlock(DyeColor.LIGHT_BLUE, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block YELLOW_BACKPACK = create("yellow_backpack", new BackpackBlock(DyeColor.YELLOW, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block LIME_BACKPACK = create("lime_backpack", new BackpackBlock(DyeColor.LIME, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block PINK_BACKPACK = create("pink_backpack", new BackpackBlock(DyeColor.PINK, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block GRAY_BACKPACK = create("gray_backpack", new BackpackBlock(DyeColor.GRAY, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block LIGHT_GRAY_BACKPACK = create("light_gray_backpack", new BackpackBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block CYAN_BACKPACK = create("cyan_backpack", new BackpackBlock(DyeColor.CYAN, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block PURPLE_BACKPACK = create("purple_backpack", new BackpackBlock(DyeColor.PURPLE, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block BLUE_BACKPACK = create("blue_backpack", new BackpackBlock(DyeColor.BLUE, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block BROWN_BACKPACK = create("brown_backpack", new BackpackBlock(DyeColor.BROWN, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block GREEN_BACKPACK = create("green_backpack", new BackpackBlock(DyeColor.GREEN, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block RED_BACKPACK = create("red_backpack", new BackpackBlock(DyeColor.RED, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));
	public static final Block BLACK_BACKPACK = create("black_backpack", new BackpackBlock(DyeColor.BLACK, AbstractBlock.Settings.of(
			Material.WOOL).sounds(BlockSoundGroup.WOOL).strength(0.2F).nonOpaque()));

	//-----Registry-----//
	public static void register()
	{
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));

		for(Block block : BLOCKS.keySet())
		{
			Registry.register(Registry.ITEM, BLOCKS.get(block), getItem((BackpackBlock) block));
		}
	}

	public static BackpackItem getItem(BackpackBlock block)
	{
		return new BackpackItem(block);
	}

	private static <T extends Block> T create(String name, T block)
	{
		BLOCKS.put(block, new Identifier(CamsBackpacks.MOD_ID, name));
		return block;
	}
}
