package io.seekankan.github.kansky.forge;

import org.bukkit.inventory.ItemStack;

public class ForgeMeta {
    private long startTime;
    private String item;
    private ItemStack[] rawMaterial;

    public ForgeMeta(){
        this(null,null);
    }
    public ForgeMeta(String item,ItemStack[] rawItems) {
        this(System.currentTimeMillis(),item,rawItems);
    }
    ForgeMeta(long startTime, String item,ItemStack[] rawItems) {
        this.startTime = startTime;
        this.item = item;
        this.rawMaterial = rawItems;
    }

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public String getItem() {
        return item;
    }
    public ItemStack[] getRawMaterial() {
        return rawMaterial;
    }

    public void setItem(String item) {
        this.item = item;
    }
    public ItemStack generateItem() {
        return null;
    }
    public ForgeItem getForgeItem() {
        return Forge.getForgeConfig().getRecipe().get(item);
    }

}
