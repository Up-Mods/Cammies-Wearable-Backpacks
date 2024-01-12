package dev.cammiescorner.camsbackpacks.init;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {

    @SuppressWarnings("unchecked")
    public static final Supplier<BlockEntityType<BackpackBlockEntity>> BACKPACK = create("backpack", () -> BackpackBlockEntity::new, Services.REGISTRY.getModBlocks().toArray(Supplier[]::new));

    @SafeVarargs
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> create(String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> factory, Supplier<Block>... allowedBlocks) {
        return Services.REGISTRY.createBlockEntityType(name, factory, allowedBlocks);
    }

    public static void register() {
        // NO-OP
        // needed to load the class
    }
}
