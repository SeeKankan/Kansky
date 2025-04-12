package io.seekankan.github.kansky.event;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class KanItemEvent extends Event {
    private final ItemStack itemStack;

    public KanItemEvent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
