package io.seekankan.github.kansky.forge;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class Forge {
    private static YamlConfiguration forgeYAMLConfig;
    private static ForgeConfig forgeConfig;
    public static void loadForge(){
//        Kansky instance = Kansky.getInstance();
//        File file = new File(instance.getDataFolder(),"forge.yml");
//        if(!file.exists()) {
//            instance.getLogger().info("Create forge.yml");
//            instance.saveResource("forge.yml",true);
//        }
        forgeYAMLConfig = KanskyUtil.getConfig("forge.yml"); //YamlConfiguration.loadConfiguration(file);
        forgeConfig = ForgeConfig.resolveConfig(forgeYAMLConfig);

       DefaultForgeFunction.loadDefForgeFunction();
    }
    public static void openMainForgeGUI(Player player){
        ForgeMainGUIConfig mainGUIConfig = forgeConfig.getMainGUIConfig();
        player.openInventory(mainGUIConfig.createGUI(player));
    }
    public static void openRecipeGUI(Player player,int slot,int page){
        ForgeRecipeGUIConfig recipeGUIConfig = forgeConfig.getRecipeGUIConfig();
        Inventory inventory = recipeGUIConfig.createGUI(slot,page);
        if(inventory != null) player.openInventory(inventory);
    }
    public static ForgeConfig getForgeConfig() {
        return forgeConfig;
    }
}
