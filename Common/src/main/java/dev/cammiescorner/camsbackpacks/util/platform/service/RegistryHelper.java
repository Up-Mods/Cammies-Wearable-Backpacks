package dev.cammiescorner.camsbackpacks.util.platform.service;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface RegistryHelper {

    <T extends BlockEntity> Supplier<BlockEntityType<T>> createBlockEntityType(String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> factory, Supplier<Block>[] supportedBlocks);

    Stream<Supplier<? extends Block>> getModBlocks();

    <T extends Item> Supplier<T> createItem(String name, Supplier<T> item);

    <T extends Block> Supplier<T> createBlock(String name, Supplier<T> block);

    <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String name, Supplier<MenuExtendedFactory<T>> factory);

    @FunctionalInterface
    interface MenuExtendedFactory<T extends AbstractContainerMenu> {
        T create(int syncId, Inventory inventory, FriendlyByteBuf buf);
    }
}
