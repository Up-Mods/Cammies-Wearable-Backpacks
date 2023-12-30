package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.LinkedHashMap;

public class ModBlocks {

    public static final LinkedHashMap<Block, ResourceLocation> BLOCKS = new LinkedHashMap<>();

    public static final Block WHITE_BACKPACK = create("white_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block ORANGE_BACKPACK = create("orange_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.ORANGE));
    public static final Block MAGENTA_BACKPACK = create("magenta_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_BACKPACK = create("light_blue_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_BACKPACK = create("yellow_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.YELLOW));
    public static final Block LIME_BACKPACK = create("lime_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIME));
    public static final Block PINK_BACKPACK = create("pink_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.PINK));
    public static final Block GRAY_BACKPACK = create("gray_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.GRAY));
    public static final Block LIGHT_GRAY_BACKPACK = create("light_gray_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIGHT_GRAY));
    public static final Block CYAN_BACKPACK = create("cyan_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.CYAN));
    public static final Block PURPLE_BACKPACK = create("purple_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.PURPLE));
    public static final Block BLUE_BACKPACK = create("blue_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BLUE));
    public static final Block BROWN_BACKPACK = create("brown_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BROWN));
    public static final Block GREEN_BACKPACK = create("green_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.GREEN));
    public static final Block RED_BACKPACK = create("red_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.RED));
    public static final Block BLACK_BACKPACK = create("black_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BLACK));
    public static final Block GAY_BACKPACK = create("gay_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block LESBIAN_BACKPACK = create("lesbian_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block QPOC_BACKPACK = create("qpoc_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block BI_BACKPACK = create("bi_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block PAN_BACKPACK = create("pan_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block TRANS_BACKPACK = create("trans_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block GENDERQUEER_BACKPACK = create("genderqueer_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block GENDERFLUID_BACKPACK = create("genderfluid_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block ENBY_BACKPACK = create("enby_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block ACE_BACKPACK = create("ace_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block DEMI_BACKPACK = create("demi_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block ARO_BACKPACK = create("aro_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Block AGENDER_BACKPACK = create("agender_pride_backpack", new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));

    public static void register() {
        BLOCKS.keySet().forEach(block -> Registry.register(BuiltInRegistries.BLOCK, BLOCKS.get(block), block));

        for (Block block : BLOCKS.keySet()) {
            Registry.register(BuiltInRegistries.ITEM, BLOCKS.get(block), getItem((BackpackBlock) block));
            ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> content.accept(block));
        }
    }

    private static BackpackItem getItem(BackpackBlock block) {
        return new BackpackItem(block, new Item.Properties());
    }

    private static <T extends Block> T create(String name, T block) {
        BLOCKS.put(block, CamsBackpacks.id(name));
        return block;
    }
}
