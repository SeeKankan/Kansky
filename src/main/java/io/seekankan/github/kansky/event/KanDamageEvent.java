package io.seekankan.github.kansky.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

/**
 * You cannot modify damage in this event.
 * You should modify damage by creating attribute tracker or create damage modifier.
 * This event help other plugin to add ability like when hit mob add health.
 * If you want to cancel this event,please register a EntityDamageListener that EventPriority = LOWEST
 */
public class KanDamageEvent extends KanEvent{
    private final LivingEntity entity;
    private final LivingEntity damager;
    private final Projectile arrow;
    private final double damage;
    private static final HandlerList handlers = new HandlerList();

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public KanDamageEvent(LivingEntity entity, LivingEntity damager, Projectile arrow, double damage) {
        this.entity = entity;
        this.damager = damager;
        this.arrow = arrow;
        this.damage = damage;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public @Nullable LivingEntity getDamager() {
        return damager;
    }

    public @Nullable Projectile getArrow() {
        return arrow;
    }

    public double getDamage() {
        return damage;
    }
}
