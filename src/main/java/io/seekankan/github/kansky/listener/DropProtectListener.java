package io.seekankan.github.kansky.listener;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropProtectListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onlyPickupSelfDrop(PlayerDropItemEvent event){
        if(Config.onlyPickSelfDrop)
        NBT.modify(event.getItemDrop(),nbt -> {
            if(!nbt.hasTag("Owner")) {
                nbt.setString("Owner",nbt.hasTag("Thrower") ? nbt.getString("Thrower") : event.getPlayer().getName());
            }
        });
    }
}
