package dev.cammiescorner.camsbackpacks.util.platform;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.util.platform.service.MenuHelper;
import dev.cammiescorner.camsbackpacks.util.platform.service.PlatformHelper;
import dev.cammiescorner.camsbackpacks.util.platform.service.RegistryHelper;
import org.slf4j.Logger;

import java.util.ServiceLoader;

public class Services {

    private static final Logger logger = LogUtils.getLogger();

    public static final PlatformHelper PLATFORM = load(PlatformHelper.class);
    public static final MenuHelper MENU = load(MenuHelper.class);
    public static final RegistryHelper REGISTRY = load(RegistryHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        logger.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
