package dev.cammiescorner.camsbackpacks.config;


import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@ConfigInfo(titleTranslation = "config_category.camsbackpacks.client")
@Category(value = "client")
public final class ClientConfig {
    @ConfigEntry(id = "sneakPlaceBackpack", type = EntryType.BOOLEAN, translation = "config.camsbackpacks.sneakPlaceBackpack")
    public static boolean sneakPlaceBackpack = true;
}
