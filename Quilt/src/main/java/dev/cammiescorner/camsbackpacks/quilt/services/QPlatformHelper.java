package dev.cammiescorner.camsbackpacks.quilt.services;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.util.platform.service.PlatformHelper;
import org.quiltmc.loader.api.QuiltLoader;

public class QPlatformHelper implements PlatformHelper {

    @Override
    public boolean isModLoaded(String modid) {
        return QuiltLoader.isModLoaded(modid);
    }

    @Override
    public String getIssuesUrl() {
        return QuiltLoader.getModContainer(CamsBackpacks.MOD_ID).map(mod -> mod.metadata().getContactInfo("issues")).orElse(CamsBackpacks.MOD_ID);
    }
}
