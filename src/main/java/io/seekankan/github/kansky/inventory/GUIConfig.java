package io.seekankan.github.kansky.inventory;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.util.ItemCreator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GUIConfig {
    public static final String WRITE_ITEM_COMMAND = "kancommand";
    public static final String WRITE_ITEM_CLICK_CLOSE = "kanclickwhenclose";
    private int size;
    private ItemStack[] items;
    private ItemStack fill;
    private String title;
    public GUIConfig(@NotNull ConfigurationSection config,@Nullable Player papiPlayer){
        size = config.getInt("size");
        items = new ItemStack[size];
        ConfigurationSection menu = config.getConfigurationSection("menu");
        for(String key : menu.getKeys(false)) {
            ConfigurationSection itemConfig = menu.getConfigurationSection(key);
            List<String> loreRaw = itemConfig.getStringList("lore");
            List<String> lore = papiPlayer == null ? loreRaw : PlaceholderAPI.setPlaceholders(papiPlayer,loreRaw);
            ItemStack item = new ItemCreator(Material.valueOf(itemConfig.getString("material")))
                    .name(itemConfig.getString("name"))
                    .lore(lore)
                    .create();
            NBT.modify(item,itemNBT -> {
                Optional.ofNullable(itemConfig.getString("command")).ifPresent(command -> itemNBT.setString(WRITE_ITEM_COMMAND,command));
                itemNBT.setBoolean(WRITE_ITEM_CLICK_CLOSE,itemConfig.getBoolean("close_when_click",false));
            });
            items[Integer.parseInt(key)] = item;

        }
        fill = new ItemStack(Material.valueOf(config.getString("fill")));
        title = config.getString("title");
    }
    public Inventory createInventory(InventoryHolder holder) {
        Inventory inventory = Bukkit.createInventory(holder,size,title);
        for(int index = 0;index < items.length;index++){
            inventory.setItem(index,items[index] == null? fill : items[index]);
        }
        return inventory;
    }
}
