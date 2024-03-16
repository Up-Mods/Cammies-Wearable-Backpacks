package dev.cammiescorner.camsbackpacks;

import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;
import dev.cammiescorner.camsbackpacks.init.ModBlockEntities;
import dev.cammiescorner.camsbackpacks.init.ModBlocks;
import dev.cammiescorner.camsbackpacks.init.ModScreenHandlers;
import dev.cammiescorner.camsbackpacks.util.platform.Services;
import net.minecraft.resources.ResourceLocation;

public class CamsBackpacks {

    public static final String MOD_ID = "camsbackpacks";
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    public static void init() {
        CONFIGURATOR.register(BackpacksConfig.class);

        ModBlocks.register();
        ModBlockEntities.register();
        ModScreenHandlers.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String getIssuesURL() {
        return Services.PLATFORM.getIssuesUrl();
    }
}
