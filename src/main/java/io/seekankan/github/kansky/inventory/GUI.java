package io.seekankan.github.kansky.inventory;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.io.File;

public class GUI {
    private static YamlConfiguration guiConfig;
    public static void loadGui() {
//        Kansky instance = Kansky.getInstance();
//        File file = new File(instance.getDataFolder(),"gui.yml");
//        if(!file.exists()) {
//            instance.getLogger().info("Create gui.yml");
//            instance.saveResource("gui.yml",true);
//        }
        guiConfig = KanskyUtil.getConfig("gui.yml"); //YamlConfiguration.loadConfiguration(file);
    }
    public static Inventory createInventory(String guiType, InventoryHolder holder, Player player){
        return new GUIConfig(guiConfig.getConfigurationSection(guiType),player).createInventory(holder);
    }
}
