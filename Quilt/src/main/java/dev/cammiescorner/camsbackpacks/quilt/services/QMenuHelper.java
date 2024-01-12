package dev.cammiescorner.camsbackpacks.quilt.services;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.util.platform.service.MenuHelper;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
}
