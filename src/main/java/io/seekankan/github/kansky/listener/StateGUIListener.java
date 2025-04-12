package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.inventory.StateInventoryHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class StateGUIListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void cancelClick(InventoryClickEvent event) {
        if(event.getInventory().getHolder() instanceof StateInventoryHolder) event.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void cancelDrag(InventoryDragEvent event) {
        if(event.getInventory().getHolder() instanceof StateInventoryHolder) event.setCancelled(true);
    }
}
