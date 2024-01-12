package dev.cammiescorner.camsbackpacks.quilt.compat.modmenu;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.config.BackpacksConfig;

public class ModMenuEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            var config = CamsBackpacks.CONFIGURATOR.getConfig(BackpacksConfig.class);
            if(config != null) {
                return new ConfigScreen(parent, null, config);
            }

            return null;
        };
    }
}
