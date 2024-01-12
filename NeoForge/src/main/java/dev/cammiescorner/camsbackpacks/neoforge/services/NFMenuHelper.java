package dev.cammiescorner.camsbackpacks.neoforge.services;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import dev.cammiescorner.camsbackpacks.util.platform.service.MenuHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
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
}
