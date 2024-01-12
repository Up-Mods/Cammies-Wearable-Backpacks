package dev.cammiescorner.camsbackpacks.quilt;

import org.objectweb.asm.tree.ClassNode;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CamsBackpacksMixinConfigPlugin implements IMixinConfigPlugin {

    private static final String HEPHAESTUS_MOD_ID = "tconstruct";
    private static final String INVENTORIO_MOD_ID = "inventorio";

    @Override
    public void onLoad(String mixinPackage) {
        // NO-OP
    }

    @Override
    public String getRefMapperConfig() {
        // NO-OP
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "dev.cammiescorner.camsbackpacks.quilt.mixin.compat.InventorioArmorSlotMixin" -> QuiltLoader.isModLoaded(INVENTORIO_MOD_ID);
            case "dev.cammiescorner.camsbackpacks.quilt.mixin.compat.HephaestusArmorSlotMixin" -> QuiltLoader.isModLoaded(HEPHAESTUS_MOD_ID);
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // NO-OP
    }

    @Override
    public List<String> getMixins() {
        // NO-OP
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // NO-OP
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // NO-OP
    }
}
