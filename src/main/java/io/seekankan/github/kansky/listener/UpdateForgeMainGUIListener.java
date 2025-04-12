package io.seekankan.github.kansky.listener;

import com.google.common.base.Supplier;
import io.seekankan.github.kansky.forge.Forge;
import io.seekankan.github.kansky.inventory.ForgeMainGUIHolder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class UpdateForgeMainGUIListener extends BukkitRunnable implements Listener {
    private static final HashMap<Player,Inventory> updatePlayer = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void putUpdatePlayer(InventoryOpenEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        if(event.getInventory().getHolder() instanceof ForgeMainGUIHolder && humanEntity instanceof Player) {
            updatePlayer.put((Player) humanEntity,event.getInventory());
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void removeUpdatePlayer(InventoryCloseEvent event) {
        HumanEntity humanEntity = event.getPlayer();
        if(event.getInventory().getHolder() instanceof ForgeMainGUIHolder && humanEntity instanceof Player) {
            updatePlayer.remove((Player) humanEntity,event.getInventory());
        }
    }
    @Override
    public void run() {
        updatePlayer.forEach((player, inventory) -> Forge.getForgeConfig().getMainGUIConfig().refreshInventory(player,inventory));
    }
}
