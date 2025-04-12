package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.forge.Forge;
import io.seekankan.github.kansky.forge.ForgeDataUtil;
import io.seekankan.github.kansky.forge.ForgeMainGUIConfig;
import io.seekankan.github.kansky.forge.ForgeMeta;
import io.seekankan.github.kansky.inventory.ForgeMainGUIHolder;
import io.seekankan.github.kansky.inventory.ForgeRecipeGUIHolder;
import io.seekankan.github.kansky.inventory.StateInventoryHolder;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ForgeMainGUIListener implements Listener {
    @EventHandler(priority = EventPriority.LOW,ignoreCancelled = true)
    public void handleClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        InventoryHolder holder = event.getInventory().getHolder();
//        if(inventory == null || inventory instanceof PlayerInventory) return;
//        if(!(inventory.getHolder() instanceof ForgeMainGUIHolder)) return;
//        event.setCancelled(true);
//        ItemStack itemStack = inventory.getItem(event.getSlot());
//        Integer slotRaw = ForgeMainGUIConfig.getForgeSlotByItem(itemStack);
//        if(slotRaw == null) return;
//        int slot = slotRaw;
//        if(!(event.getWhoClicked() instanceof Player)) return;
//        Forge.openRecipeGUI((Player) event.getWhoClicked(),slot,0);
        if(KanskyUtil.filterInventory(event, ForgeMainGUIHolder.class)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = inventory.getItem(event.getSlot());
        Integer slotRaw = ForgeMainGUIConfig.getForgeSlotByItem(itemStack);
        if(slotRaw == null) return;
        int slot = slotRaw;
        ForgeMeta forgeMeta = ForgeDataUtil.getForgeMeta(player,slot);
        if(forgeMeta.getForgeItem() == null) Forge.openRecipeGUI((Player) event.getWhoClicked(),slot,0);
        else {
            if(ForgeDataUtil.getEndTime(forgeMeta) > 0) return;
            KanskyUtil.addItemOverflow(player,forgeMeta.getForgeItem()
                    .createItemByItems(Arrays.asList(forgeMeta.getRawMaterial())));
            ForgeDataUtil.setForgeMeta(player,slot,new ForgeMeta());
            Forge.getForgeConfig().getMainGUIConfig().refreshInventory(player,inventory);
        }
    }
    @EventHandler(priority = EventPriority.LOW,ignoreCancelled = true)
    public void handleDrag(InventoryDragEvent event){
        if(event.getInventory().getHolder() instanceof ForgeMainGUIHolder) event.setCancelled(true);
    }
}
