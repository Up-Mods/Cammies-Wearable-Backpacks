package dev.cammiescorner.camsbackpacks.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;

@Config(value = CamsBackpacks.MOD_ID, categories = {
        ClientConfig.class
})
@ConfigInfo(
        title = "Cammie's Wearable Backpacks",
        description = """
                Just a backpack that lets you carry more items with you.
                Requires an empty chest slot though.
                """,
        links = {
                @ConfigInfo.Link(
                        value = "https://modrinth.com/mod/s2OAgsTb",
                        icon = "modrinth",
                        text = "Modrinth"
                ),
                @ConfigInfo.Link(
                        value = "https://www.curseforge.com/projects/443091",
                        icon = "curseforge",
                        text = "Curseforge"
                ),
                @ConfigInfo.Link(
                        value = "https://github.com/Up-Mods/Cammies-Wearable-Backpacks",
                        icon = "github",
                        text = "Github"
                )
        }
)
public final class BackpacksConfig {

    @ConfigEntry(id = "allowInventoryGui", type = EntryType.BOOLEAN, translation = "config.camsbackpacks.allowInventoryGui")
    public static boolean allowInventoryGui = true;
}
