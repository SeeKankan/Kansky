package io.seekankan.github.kansky.listener;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.inventory.GUIConfig;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class ExeCommandGUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        performCommand(event.getWhoClicked(),event.getCurrentItem());
    }
    @EventHandler
    public void onClick(InventoryDragEvent event) {
        performCommand(event.getWhoClicked(),event.getCursor());
    }
    private static void performCommand(HumanEntity human,ItemStack itemStack) {
        if(!(human instanceof Player)) return;
        if(!KanskyUtil.isItemStack(itemStack)) return;
        NBT.get(itemStack,nbt -> {
            String command = nbt.getString(GUIConfig.WRITE_ITEM_COMMAND);
            Boolean clickWhenClose = nbt.getBoolean(GUIConfig.WRITE_ITEM_CLICK_CLOSE);
            if(!(command == null || "".equals(command))) {
                ((Player)(human)).performCommand(command);
            }
            if(clickWhenClose) {
                human.closeInventory();
            }
        });
    }
}
