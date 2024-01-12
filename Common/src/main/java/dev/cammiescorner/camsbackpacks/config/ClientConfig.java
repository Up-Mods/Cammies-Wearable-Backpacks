package dev.cammiescorner.camsbackpacks.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "client", translation = "config_category.camsbackpacks.client")
public final class ClientConfig {
    @ConfigEntry(id = "sneakPlaceBackpack", type = EntryType.BOOLEAN, translation = "config.camsbackpacks.sneakPlaceBackpack")
    public static boolean sneakPlaceBackpack = true;
}
