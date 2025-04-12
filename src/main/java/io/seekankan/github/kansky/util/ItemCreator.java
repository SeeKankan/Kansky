package io.seekankan.github.kansky.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemCreator {
    private ItemStack itemStack;
    public ItemCreator(Material material){
        this(new ItemStack(material));
    }
    public ItemCreator(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    public ItemCreator amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    public ItemCreator name(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemCreator lore(List<String> lores) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lores);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemStack create() {
        return itemStack;
    }
}
