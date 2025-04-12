package io.seekankan.github.kansky.forge;

import io.seekankan.github.kansky.event.KanCollectForgeItemEvent;
import io.seekankan.github.kansky.kanattribute.DefaultItem;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeItem {
    private long time;
    private Map<String,Integer> recipe;
    private String name;
    private String forgeFunction;
    private List<String> functionArgs;

    public ForgeItem(long time, Map<String, Integer> recipe, String name, String forgeFunction, List<String> functionArgs) {
        this.time = time;
        this.recipe = recipe;
        this.name = name;
        this.forgeFunction = forgeFunction;
        this.functionArgs = functionArgs;
    }

    public static ForgeItem resolveConfig(ConfigurationSection config, String name){
        long time = config.getLong("time");
        Map<String,Integer> recipe = new HashMap<>();
        ConfigurationSection recipeConfig = config.getConfigurationSection("recipe");
        for(String key : recipeConfig.getKeys(false)){
            recipe.put(key,recipeConfig.getInt(key));
        }
        ConfigurationSection thenConfig = config.getConfigurationSection("then");
        String forgeFunction = null;
        List<String> functionArgs = new ArrayList<>();
        if(thenConfig != null) {
            forgeFunction = thenConfig.getString("function");
            functionArgs = KanskyUtil.getNotNull(thenConfig.getStringList("args"),new ArrayList<>());
        }
        return new ForgeItem(time,recipe,name,forgeFunction,functionArgs);
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getRecipe() {
        return recipe;
    }
    public List<String> formatRecipe() {
        ArrayList<String> lore = new ArrayList<>();
        recipe.forEach((key,value) -> {
            ItemConfig itemConfig = ItemConfig.getItemConfig(key);
            if(itemConfig == null) return;
            lore.add(itemConfig.displayName + ": " + value);
        });
        return lore;
    }

    public ItemStack createItem() {
        return DefaultItem.createItem(name);
    }
    public ItemStack createItemByItems(List<ItemStack> items) {
        DefaultItem template = DefaultItem.getDefaultItemMap().get(name);
        ItemStack baseItem = createItem();
        if(forgeFunction != null) {
            ForgeFunctionRegistry.invokeFunction(forgeFunction,baseItem,items,functionArgs);
        }
        KanCollectForgeItemEvent callEvent = new KanCollectForgeItemEvent(baseItem,template,items);
        Bukkit.getPluginManager().callEvent(callEvent);
        return baseItem;
    }

    @Override
    public String toString() {
        return "ForgeItem{" +
                "time=" + time +
                ", recipe=" + recipe +
                '}';
    }
}
