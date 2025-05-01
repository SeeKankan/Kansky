package io.seekankan.github.kansky.event;

import org.bukkit.event.HandlerList;

public class KanUpdateLoreEvent extends KanEvent{
    private static final HandlerList handlers = new HandlerList();

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


}
