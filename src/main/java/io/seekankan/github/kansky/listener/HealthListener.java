package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.Config;
import io.seekankan.github.kansky.kanattribute.AttributeTracker;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class HealthListener extends BukkitRunnable implements Listener{
    private static final UUID HEALTH_UUID = new UUID(0xe59c449b,0xcc496f3d);
    private static final String DISPLAY_NAME = "kansky_display_name";
    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(HealthListener::updateHealth));
    }
    public static void updateHealth(Entity entity) {
        if(!(entity instanceof LivingEntity)) return;
        LivingEntity livingEntity = (LivingEntity) entity;
        AttributeInstance ai = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double addHealth = AttributeTracker.trackModifier(livingEntity).getOrDefault("health",0d);
        AttributeModifier modifier = new AttributeModifier(HEALTH_UUID,
                "kanskyHealth",
                addHealth,
                AttributeModifier.Operation.ADD_NUMBER);
        ai.removeModifier(modifier);
        ai.addModifier(modifier);
        if(livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
//            player.sendMessage("kansky health: " + addHealth);
//            player.sendMessage("attributes: " + ai.getModifiers());
//            player.sendMessage("base: " + ai.getBaseValue());
//            player.sendMessage("your max health: " + maxHealth);
            if(maxHealth > Config.healthScale) {
                player.setHealthScale(Config.healthScale);
                player.setHealthScaled(true);
            } else {
                player.setHealthScaled(false);
            }
        }
    }
//    @EventHandler(ignoreCancelled = true)
//    public void inventory(InventoryEvent event) {
//        event.getViewers().forEach(HealthListener::updateHealth);
//    }

//    @EventHandler(ignoreCancelled = true)
//    public void updateHealthList(EntityDamageEvent event) {
//        if(!Config.doListHealth) return;
//        Entity entity = event.getEntity();
//        if(!(entity instanceof LivingEntity)) return;
//        NBT.modify(entity,nbt -> {
//            if(!(nbt.getString(DISPLAY_NAME) == null)){
//                nbt.setString(DISPLAY_NAME,KanskyUtil.getEntityName(entity));
//            }
//            double health0 = ((LivingEntity) entity).getHealth() - event.getFinalDamage();
//            double health = Math.max(Math.floor(health0),0);
//            System.out.println(nbt.getString(DISPLAY_NAME));
//            String customName = nbt.getString(DISPLAY_NAME) + " " + health;
//            entity.setCustomName(null);
//            entity.setCustomName(customName);
//        });
//    }
}
