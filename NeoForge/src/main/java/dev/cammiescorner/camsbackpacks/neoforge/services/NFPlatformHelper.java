package dev.cammiescorner.camsbackpacks.neoforge.services;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.util.platform.service.PlatformHelper;
import net.minecraftforge.fml.ModList;

public class NFPlatformHelper implements PlatformHelper {
    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String getIssuesUrl() {
        return ModList.get().getModContainerById(CamsBackpacks.MOD_ID).orElseThrow().getModInfo().getOwningFile().getFileProperties().get("issueTrackerURL") instanceof String value ? value : CamsBackpacks.MOD_ID;
    }
}
