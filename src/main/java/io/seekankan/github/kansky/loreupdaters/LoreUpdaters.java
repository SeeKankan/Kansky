package io.seekankan.github.kansky.loreupdaters;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoreUpdaters {
    private static YamlConfiguration loreUpdaterYAML;
    private static LoreUpdater loreUpdater;
    
    public static void loadLoreUpdater() {
        loreUpdaterYAML = KanskyUtil.getConfig("lore_updaters.yml");
        loreUpdater = new DefaultLoreUpdater(loreUpdaterYAML);
    }
    public static LoreUpdater getLoreUpdater() {
        return loreUpdater;
    }
//    static YamlConfiguration getConfig() {
//        return loreUpdaterYAML;
//    }
}
