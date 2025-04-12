package io.seekankan.github.kansky.event;

import io.seekankan.github.kansky.kanattribute.DefaultItem;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KanCollectForgeItemEvent extends KanItemEvent{
    private final DefaultItem defaultItem;
    private final List<ItemStack> forgeMaterial;
    private static final HandlerList handlers = new HandlerList();

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public KanCollectForgeItemEvent(ItemStack itemStack, DefaultItem defaultItem, List<ItemStack> forgeMaterial) {
        super(itemStack);
        this.defaultItem = defaultItem;
        this.forgeMaterial = forgeMaterial;
    }

    public DefaultItem getDefaultItem() {
        return defaultItem;
    }

    public List<ItemStack> getForgeMaterial() {
        return forgeMaterial;
    }
}
