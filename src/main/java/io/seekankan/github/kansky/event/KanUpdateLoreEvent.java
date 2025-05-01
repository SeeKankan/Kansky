package io.seekankan.github.kansky.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KanUpdateLoreEvent extends KanEvent{
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack itemStack;
    private final List<String> lore;

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public KanUpdateLoreEvent(Player player, ItemStack itemStack, List<String> lore) {
        this.player = player;
        this.itemStack = itemStack;
        this.lore = lore;
    }

    public Player getPlayer() {
        return player;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
    public List<String> getLore() {
        return lore;
    }
}
