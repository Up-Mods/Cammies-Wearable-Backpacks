package dev.cammiescorner.camsbackpacks.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.Link;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;

@Config(CamsBackpacks.MOD_ID)
@WebInfo(
        title = "Cammie's Wearable Backpacks",
        color = "#ff0000",
        description = """
                Just a backpack that lets you carry more items with you.
                Requires an empty chest slot though.
                """,
        links = {
                @Link(
                        value = "https://modrinth.com/mod/s2OAgsTb",
                        icon = "modrinth",
                        title = "Modrinth"
                ),
                @Link(
                        value = "https://www.curseforge.com/projects/443091",
                        icon = "curseforge",
                        title = "Curseforge"
                ),
                @Link(
                        value = "https://github.com/Up-Mods/Cammies-Wearable-Backpacks",
                        icon = "github",
                        title = "Github"
                )
        }
)
public final class BackpacksConfig {

    @ConfigEntry(id = "allowInventoryGui", type = EntryType.BOOLEAN, translation = "config.camsbackpacks.allowInventoryGui")
    public static boolean allowInventoryGui = true;

    @InlineCategory
    public static ClientConfig clientConfig;
}
