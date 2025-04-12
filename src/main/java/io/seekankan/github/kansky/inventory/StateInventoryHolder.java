package io.seekankan.github.kansky.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StateInventoryHolder implements ExeCommandInvHolder {
    private Player player;
    public StateInventoryHolder(Player player){
        this.player = player;
    }
    @Override
    public Inventory getInventory() {
        if(player == null) return null;
        return player.getOpenInventory().getTopInventory();
    }
}
