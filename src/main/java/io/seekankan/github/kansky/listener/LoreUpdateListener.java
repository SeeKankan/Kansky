package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.loreupdaters.LoreUpdater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/*
 PlayerInteractEvent, //不需要更新
 PlayerItemHeldEvent, //不需要更新
 PlayerPickupItemEvent, //已被弃用
 PlayerSwapHandItemsEvent, //不需要更新
 EntityPickupItemEvent
 */
public class LoreUpdateListener implements Listener {

    private static void updateItem(Player player, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(LoreUpdater.getNewLore(player, itemStack));
        itemStack.setItemMeta(meta);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPickUpItem(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        ItemStack itemStack = event.getItem().getItemStack();
        updateItem(
                (Player) event.getEntity(),
                itemStack
        );
//        event.getItem().setItemStack(itemStack);
    }
//    @EventHandler(ignoreCancelled = true)
//    public void onPlayerDropItem(PlayerDropItemEvent event) {
//        System.out.println("update");
//        ItemStack itemStack = event.getItemDrop().getItemStack();
//        updateItem(
//                event.getPlayer(),
//                itemStack
//        );
//        event.getItemDrop().setItemStack(itemStack);
//    }
}
