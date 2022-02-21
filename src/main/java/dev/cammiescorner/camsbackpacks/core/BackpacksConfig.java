package dev.cammiescorner.camsbackpacks.core;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = CamsBackpacks.MOD_ID)
public class BackpacksConfig implements ConfigData {
    public boolean sneakPlaceBackpack = true;

    public static BackpacksConfig get() {
        return AutoConfig.getConfigHolder(BackpacksConfig.class).getConfig();
    }
}
