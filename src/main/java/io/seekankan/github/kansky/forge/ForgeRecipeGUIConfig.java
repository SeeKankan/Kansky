package io.seekankan.github.kansky.forge;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.inventory.ForgeRecipeGUIHolder;
import io.seekankan.github.kansky.util.ItemCreator;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForgeRecipeGUIConfig {
    public enum ClickAction {
        PREVIOUS_PAGE,
        NEXT_PAGE,
        SELECT
    }
    private static final String ACTION_KEY = "action";
    private static final String FORGE_KEY = "forgeitemname";

    private int size;
    private Material pageMaterial;
    private int previousPageIndex;
    private int nextPageIndex;
    private String previousPageName;
    private String nextPageName;
    private Material fillMaterial;
    private int fillStart;
    private int fillLast;
    private List<String> format;
    private ForgeItem[][] showRecipe;

    public void initShowRecipe() {
        int itemPreList = size - 2 - (fillLast - fillStart);
        Map<String,ForgeItem> recipes = Forge.getForgeConfig().getRecipe();
        ForgeItem[] allItem = getItems(recipes);
        showRecipe = new ForgeItem[KanskyUtil.roundUp(recipes.size(),itemPreList)][itemPreList];
        for(int i = 0;i < allItem.length;i++) {
            showRecipe[i / itemPreList][i % itemPreList] = allItem[i];
        }
    }
    public ForgeRecipeGUIConfig(int size, Material pageMaterial, int previousPageIndex, int nextPageIndex, String previousPageName, String nextPageName, Material fillMaterial, int fillStart, int fillLast,List<String> format) {
        this.size = size;
        this.pageMaterial = pageMaterial;
        this.previousPageIndex = previousPageIndex;
        this.nextPageIndex = nextPageIndex;
        this.previousPageName = previousPageName;
        this.nextPageName = nextPageName;
        this.fillMaterial = fillMaterial;
        this.fillStart = fillStart;
        this.fillLast = fillLast;
        this.format = format;
    }

    public static ForgeRecipeGUIConfig resolveConfig(ConfigurationSection config){
        int size = config.getInt("size");
        Material pageMaterial = Material.valueOf(config.getString("page_material"));
        int previousPageIndex = config.getInt("previous_page");
        int nextPageIndex = config.getInt("next_page");
        String previousPageName = config.getString("previous_page_name");
        String nextPageName = config.getString("next_page_name");
        Material fillMaterial = Material.valueOf(config.getString("fill_material"));
        int fillStart = config.getInt("fill_start");
        int fillLast = config.getInt("fill_last");
        List<String> format = config.getStringList("format");
        return new ForgeRecipeGUIConfig(size,pageMaterial,previousPageIndex,nextPageIndex,previousPageName,nextPageName,fillMaterial,fillStart,fillLast,format);
    }
    public Inventory createGUI(int slot, int page){
        if(!isValidPage(page)) return null;
        Inventory inv = Bukkit.createInventory(new ForgeRecipeGUIHolder(slot,page),size,"");
        ItemStack previousPageItem = new ItemCreator(pageMaterial).name(previousPageName).create();
        ItemStack nextPageItem = new ItemCreator(pageMaterial).name(nextPageName).create();
        writeAction(previousPageItem,ClickAction.PREVIOUS_PAGE);
        writeAction(nextPageItem,ClickAction.NEXT_PAGE);
        inv.setItem(previousPageIndex,previousPageItem);
        inv.setItem(nextPageIndex,nextPageItem);
        for(int fillIndex = fillStart;fillIndex < fillLast;fillIndex++) {
            inv.setItem(fillIndex,new ItemStack(fillMaterial));
        }
        ForgeItem[] listForgeItem = showRecipe[page];
        int showIndex = 0;
        for(int index = 0;index < size;index++){
            if(index == previousPageIndex
                    || index == nextPageIndex
                    || (index>= fillStart && index < fillLast)) continue;
            ForgeItem forgeItem = listForgeItem[showIndex];
            if(forgeItem == null) continue;
            ItemStack itemStack = forgeItem.createItem();
            ItemMeta meta = itemStack.getItemMeta();
            List<String> listCost = forgeItem.formatRecipe();
            meta.setLore(formatLore(meta.getLore(),listCost));
            itemStack.setItemMeta(meta);
            writeAction(itemStack,ClickAction.SELECT);
            writeForge(itemStack,forgeItem);
            inv.setItem(index,itemStack);
            showIndex++;
        }
        return inv;
    }
    private boolean isValidPage(int page){
        return page >= 0 && page < showRecipe.length;
    }
    private List<String> formatLore(List<String> oldLore,List<String> cost) {
        ArrayList<String> retLore = new ArrayList<>();
        format.forEach(str -> {
            if("{lore}".equals(str)) {
                if(oldLore != null) retLore.addAll(oldLore);
            }
            else if("{cost}".equals(str) && cost != null) retLore.addAll(cost);
            else retLore.add(str);
        });
        return retLore;
    }
    private static void writeAction(ItemStack itemStack,ClickAction action) {
        NBT.modify(itemStack, nbt -> {
            nbt.setEnum(ACTION_KEY,action);
        });
    }
    public static ClickAction getAction(ItemStack itemStack) {
        return NBT.get(itemStack,nbt -> {
            return nbt.getEnum(ACTION_KEY,ClickAction.class);
        });
    }
    private static void writeForge(ItemStack itemStack,ForgeItem forgeItem) {
        NBT.modify(itemStack, nbt -> {
            nbt.setString(FORGE_KEY,forgeItem.getName());
        });
    }
    public static String getForge(ItemStack itemStack) {
        return NBT.get(itemStack,nbt -> {
            return nbt.getString(FORGE_KEY);
        });
    }
    private static ForgeItem[] getItems(Map<?,ForgeItem> map){
        ForgeItem[] items = new ForgeItem[map.size()];
        int index = 0;
        for(Object key : map.keySet()){
            items[index] = map.get(key);
            index++;
        }
        return items;
    }
}
