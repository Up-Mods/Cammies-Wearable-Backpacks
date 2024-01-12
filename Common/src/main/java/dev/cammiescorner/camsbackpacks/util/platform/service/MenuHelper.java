package dev.cammiescorner.camsbackpacks.util.platform.service;

import dev.cammiescorner.camsbackpacks.block.entity.BackpackBlockEntity;
import net.minecraft.world.MenuProvider;

public interface MenuHelper {

    MenuProvider getMenuProvider(BackpackBlockEntity backpack);
}
