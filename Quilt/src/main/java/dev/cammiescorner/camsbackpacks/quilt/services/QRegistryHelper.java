package dev.cammiescorner.camsbackpacks.quilt.services;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.util.platform.service.RegistryHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class QRegistryHelper implements RegistryHelper {
    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> createBlockEntityType(String name, Supplier<BlockEntityType.BlockEntitySupplier<T>> factory, Supplier<Block>[] supportedBlocks) {
        var blocks = new Block[supportedBlocks.length];
        for(int i = 0; i < supportedBlocks.length; i++) {
            blocks[i] = supportedBlocks[i].get();
        }
        var value = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CamsBackpacks.id(name), QuiltBlockEntityTypeBuilder.create(factory.get(), blocks).build());
        return () -> value;
    }

    @Override
    public Stream<Supplier<? extends Block>> getModBlocks() {
        return BuiltInRegistries.BLOCK.holders().filter(e -> e.key().location().getNamespace().equals(CamsBackpacks.MOD_ID)).sorted(Comparator.comparing(e -> e.key().location())).map(Holder.Reference::value).map(block -> () -> block);
    }

    @Override
    public <T extends Item> Supplier<T> createItem(String name, Supplier<T> item) {
        var value = Registry.register(BuiltInRegistries.ITEM, CamsBackpacks.id(name), item.get());
        return () -> value;
    }

    @Override
    public <T extends Block> Supplier<T> createBlock(String name, Supplier<T> block) {
        var value = Registry.register(BuiltInRegistries.BLOCK, CamsBackpacks.id(name), block.get());
        return () -> value;
    }

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String name, Supplier<MenuExtendedFactory<T>> factory) {
        var value = Registry.register(BuiltInRegistries.MENU, CamsBackpacks.id(name), new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> factory.get().create(syncId, inventory, buf)));
        return () -> value;
    }
}
