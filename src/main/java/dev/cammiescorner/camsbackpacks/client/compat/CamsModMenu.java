package dev.cammiescorner.camsbackpacks.client.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class CamsModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> BackpacksConfig.getScreen(parent, CamsBackpacks.MOD_ID);
    }
}
