package io.seekankan.github.kansky.event;


import io.seekankan.github.kansky.kanattribute.DefaultItem;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class KanItemCreateEvent extends KanItemEvent{
    private final DefaultItem defaultItem;
    private static final HandlerList handlers = new HandlerList();

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public KanItemCreateEvent(ItemStack itemStack, DefaultItem template) {
        super(itemStack);
        this.defaultItem = template;
    }

    public DefaultItem getDefaultItem() {
        return defaultItem;
    }
}
