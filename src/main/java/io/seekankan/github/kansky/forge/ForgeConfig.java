package io.seekankan.github.kansky.forge;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ForgeConfig {
    private int forgeSlot;
    private ForgeMainGUIConfig mainGUIConfig;
    private ForgeRecipeGUIConfig recipeGUIConfig;
    private Map<String,ForgeItem> recipe;

    public ForgeConfig(int forgeSlot, ForgeMainGUIConfig mainGUIConfig, ForgeRecipeGUIConfig recipeGUIConfig, Map<String, ForgeItem> recipe) {
        this.forgeSlot = forgeSlot;
        this.mainGUIConfig = mainGUIConfig;
        this.recipeGUIConfig = recipeGUIConfig;
        this.recipe = recipe;
    }
    public static ForgeConfig resolveConfig(ConfigurationSection config){
        int forgeSlot = config.getInt("slot");
        ForgeMainGUIConfig mainGUIConfig = ForgeMainGUIConfig.resolveConfig(config.getConfigurationSection("main_gui"));
        Map<String,ForgeItem> recipe = new HashMap<>();
        ConfigurationSection forgeItemConfig = config.getConfigurationSection("forge_item");
        for(String defItem : forgeItemConfig.getKeys(false)){
            ConfigurationSection itemConfig = forgeItemConfig.getConfigurationSection(defItem);
            recipe.put(defItem,ForgeItem.resolveConfig(itemConfig,defItem));
        }
        ForgeRecipeGUIConfig recipeGUIConfig = ForgeRecipeGUIConfig.resolveConfig(config.getConfigurationSection("choose_recipe_gui"));
        return new ForgeConfig(forgeSlot,mainGUIConfig,recipeGUIConfig,recipe);
    }

    public int getForgeSlot() {
        return forgeSlot;
    }

    public ForgeMainGUIConfig getMainGUIConfig() {
        return mainGUIConfig;
    }

    public ForgeRecipeGUIConfig getRecipeGUIConfig() {
        return recipeGUIConfig;
    }

    public Map<String, ForgeItem> getRecipe() {
        return recipe;
    }
//    public void openForgeMainGUI(Player player) {
//        player.openInventory(mainGUIConfig.createGUI(player));
//    }
}
