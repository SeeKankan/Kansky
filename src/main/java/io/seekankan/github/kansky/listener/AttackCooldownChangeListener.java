package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.UUID;

public class AttackCooldownChangeListener implements Listener {
    public static final HashMap<UUID,Long> lastAttack = new HashMap<>();
    private static void updateLastAttack(Player player) {
        lastAttack.put(player.getUniqueId(), KanskyUtil.toGameTick(System.currentTimeMillis()));
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND) updateLastAttack(event.getPlayer());
    }
    @EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent event) {
        updateLastAttack(event.getPlayer());
    }
    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent event) {
        updateLastAttack(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        updateLastAttack(damager);
    }


}
