package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityDamageBlocker implements Listener {
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void disableShield(PlayerInteractEvent event){
        event.setCancelled(!Config.shield && event.getItem() != null && event.getItem().getType().equals(Material.SHIELD));
    }

}
