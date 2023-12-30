package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlockEntities {

    private static final Map<BlockEntityType<?>, ResourceLocation> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    public static final BlockEntityType<BackpackBlockEntity> BACKPACK = create("backpack", QuiltBlockEntityTypeBuilder.create(BackpackBlockEntity::new, ModBlocks.BROWN_BACKPACK).build());

    public static void register() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, CamsBackpacks.id(name));
        return type;
    }
}
