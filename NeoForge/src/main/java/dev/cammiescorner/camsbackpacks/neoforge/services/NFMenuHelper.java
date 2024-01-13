package dev.cammiescorner.camsbackpacks.neoforge.services;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.menu.BackpackMenu;
import dev.cammiescorner.camsbackpacks.util.platform.service.MenuHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

public class NFMenuHelper implements MenuHelper {
    @Override
    public MenuProvider getMenuProvider(BackpackBlockEntity backpack) {
        return new SimpleMenuProvider(backpack::createMenu, backpack.getDisplayName());
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, BackpackBlockEntity backpack) {
        NetworkHooks.openScreen(player, menu, buf -> backpack.writeInitMenuData(player, buf));
    }

    @Override
    public void openMenu(ServerPlayer serverPlayer, ItemStack backpackStack, Container inventory) {
        BlockPos pos = serverPlayer.blockPosition();

        NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                        (syncId, playerInventory, player) -> new BackpackMenu(syncId, playerInventory, inventory, ContainerLevelAccess.create(player.level(), pos), pos, false),
                        backpackStack.getHoverName()
                ),
                buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeBoolean(false);
                }
        );
    }
}
