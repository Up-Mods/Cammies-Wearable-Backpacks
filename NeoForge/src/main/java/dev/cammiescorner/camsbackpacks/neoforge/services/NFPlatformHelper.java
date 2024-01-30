package dev.cammiescorner.camsbackpacks.neoforge.services;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.util.platform.service.PlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;

public class NFPlatformHelper implements PlatformHelper {
    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String getIssuesUrl() {
        var file = ModList.get().getModContainerById(CamsBackpacks.MOD_ID).orElseThrow().getModInfo().getOwningFile();
        return file instanceof ModFileInfo info ? info.getIssueURL().toString() : CamsBackpacks.MOD_ID;
    }
}
