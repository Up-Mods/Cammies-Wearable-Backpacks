package dev.cammiescorner.camsbackpacks.init;

import dev.cammiescorner.camsbackpacks.block.BackpackBlock;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class ModBlocks {

    public static final LinkedHashMap<Block, ResourceLocation> BLOCKS = new LinkedHashMap<>();

    public static final Supplier<BackpackBlock> WHITE_BACKPACK = create("white_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> ORANGE_BACKPACK = create("orange_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.ORANGE));
    public static final Supplier<BackpackBlock> MAGENTA_BACKPACK = create("magenta_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.MAGENTA));
    public static final Supplier<BackpackBlock> LIGHT_BLUE_BACKPACK = create("light_blue_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIGHT_BLUE));
    public static final Supplier<BackpackBlock> YELLOW_BACKPACK = create("yellow_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.YELLOW));
    public static final Supplier<BackpackBlock> LIME_BACKPACK = create("lime_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIME));
    public static final Supplier<BackpackBlock> PINK_BACKPACK = create("pink_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.PINK));
    public static final Supplier<BackpackBlock> GRAY_BACKPACK = create("gray_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.GRAY));
    public static final Supplier<BackpackBlock> LIGHT_GRAY_BACKPACK = create("light_gray_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.LIGHT_GRAY));
    public static final Supplier<BackpackBlock> CYAN_BACKPACK = create("cyan_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.CYAN));
    public static final Supplier<BackpackBlock> PURPLE_BACKPACK = create("purple_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.PURPLE));
    public static final Supplier<BackpackBlock> BLUE_BACKPACK = create("blue_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BLUE));
    public static final Supplier<BackpackBlock> BROWN_BACKPACK = create("brown_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BROWN));
    public static final Supplier<BackpackBlock> GREEN_BACKPACK = create("green_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.GREEN));
    public static final Supplier<BackpackBlock> RED_BACKPACK = create("red_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.RED));
    public static final Supplier<BackpackBlock> BLACK_BACKPACK = create("black_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.BLACK));
    public static final Supplier<BackpackBlock> GAY_BACKPACK = create("gay_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> LESBIAN_BACKPACK = create("lesbian_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> QPOC_BACKPACK = create("qpoc_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> BI_BACKPACK = create("bi_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> PAN_BACKPACK = create("pan_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> TRANS_BACKPACK = create("trans_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> GENDERQUEER_BACKPACK = create("genderqueer_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> GENDERFLUID_BACKPACK = create("genderfluid_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> ENBY_BACKPACK = create("enby_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> ACE_BACKPACK = create("ace_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> DEMI_BACKPACK = create("demi_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> ARO_BACKPACK = create("aro_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));
    public static final Supplier<BackpackBlock> AGENDER_BACKPACK = create("agender_pride_backpack", () -> new BackpackBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0.2F).noOcclusion().pushReaction(PushReaction.BLOCK), DyeColor.WHITE));

    public static void register() {
        // NO-OP
        // needed to load the class
    }

    private static Item getItem(Block block) {
        return new BackpackItem(block, new Item.Properties());
    }

    private static <T extends Block> Supplier<T> create(String name, Supplier<T> block) {
        var value = Services.REGISTRY.createBlock(name, block);
        Services.REGISTRY.createItem(name, () -> getItem(value.get()));
        return value;
    }
}
