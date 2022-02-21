package dev.cammiescorner.camsbackpacks.core;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = CamsBackpacks.MOD_ID)
public class CamsConfig implements ConfigData {
    public boolean sneakPlaceBackpack = true;

    public static CamsConfig get() {
        return AutoConfig.getConfigHolder(CamsConfig.class).getConfig();
    }
}
