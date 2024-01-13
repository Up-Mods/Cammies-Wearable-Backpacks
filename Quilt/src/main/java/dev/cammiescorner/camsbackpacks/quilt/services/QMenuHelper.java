package dev.cammiescorner.camsbackpacks.quilt.services;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.menu.BackpackMenu;
import dev.cammiescorner.camsbackpacks.util.platform.service.MenuHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class QMenuHelper implements MenuHelper {
    @Override
    public MenuProvider getMenuProvider(BackpackBlockEntity backpack) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                backpack.writeInitMenuData(player, buf);
            }

            @Override
            public Component getDisplayName() {
                return backpack.getDisplayName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
                return backpack.createMenu(syncId, inventory, player);
            }
        };
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, BackpackBlockEntity backpack) {
        player.openMenu(menu);
    }

    @Override
    public void openMenu(ServerPlayer player, ItemStack backpackStack, Container inventory) {
        BlockPos pos = player.blockPosition();
        player.openMenu(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                buf.writeBlockPos(pos);
                buf.writeBoolean(false);
            }

            @Override
            public Component getDisplayName() {
                return backpackStack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new BackpackMenu(syncId, playerInventory, inventory, ContainerLevelAccess.create(player.level(), pos), pos, false);
            }
        });
    }
}
