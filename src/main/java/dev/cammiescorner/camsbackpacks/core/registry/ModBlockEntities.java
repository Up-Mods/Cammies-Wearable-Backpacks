package dev.cammiescorner.camsbackpacks.core.registry;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.entities.BackpackBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlockEntities {
	//-----Block Entity Type Map-----//
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	public static final BlockEntityType<BackpackBlockEntity> BACKPACK = create("backpack", BlockEntityType.Builder.create(BackpackBlockEntity::new, ModBlocks.BROWN_BACKPACK).build(null));

	//-----Registry-----//
	public static void register() {
		BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
	}

	private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(type, new Identifier(CamsBackpacks.MOD_ID, name));
		return type;
	}
}
