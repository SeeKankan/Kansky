package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.Config;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.event.KanDamageEvent;
import io.seekankan.github.kansky.kanattribute.AttributeTracker;
import io.seekankan.github.kansky.util.AttackIndicator;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attributable;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import java.util.HashMap;
import java.util.Map;


public class EntityDamageListener implements Listener {
    private static final String META_DAMAGE_MODIFIER = "kandamagemodifier";
    private static final String META_ARROW_FORCE = "bowforce";
    @EventHandler(priority = EventPriority.HIGH,ignoreCancelled = true)
    public void saveArrowData(EntityShootBowEvent event){
        Kansky instance = Kansky.getInstance();
        LivingEntity entity = event.getEntity();
        Entity projectile = event.getProjectile();
        float force = event.getForce();
        Map<String, Double> damageModifier = AttributeTracker.trackModifier(entity);
        projectile.setMetadata(META_DAMAGE_MODIFIER,new FixedMetadataValue(instance,damageModifier));
        projectile.setMetadata(META_ARROW_FORCE,new FixedMetadataValue(instance,force));
    }
    @EventHandler(priority = EventPriority.LOW,ignoreCancelled = true)
    public void overrideDamage(EntityDamageEvent event) {
        double damage;
        double damageMultiplier = 1; //maybe float
        float attackCooldown = 1;
        double finalDamage;
        //override armor
        if(Config.doOverrideArmor && event.getEntity() instanceof Attributable) {
            KanskyUtil.clearArmor((Attributable) event.getEntity());
        }
//        //clear armor modifier
        if(event.isApplicable(EntityDamageEvent.DamageModifier.ARMOR)) event.setDamage(EntityDamageEvent.DamageModifier.ARMOR,0);

        if(Config.ignoredArmorEnchantments && event.isApplicable(EntityDamageEvent.DamageModifier.MAGIC)) {
            event.setDamage(EntityDamageEvent.DamageModifier.MAGIC,0);
        }

        Entity entity = event.getEntity();
        if(!(entity instanceof LivingEntity)) return;
        LivingEntity livEntity = (LivingEntity) entity;
        LivingEntity realDamager = null;
        Projectile arrow = null;
        Map<String, Double> livEntityModifier = AttributeTracker.trackModifier(livEntity);
        Map<String, Double> damagerModifier = new HashMap<>();
        if(event instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if(damager instanceof LivingEntity) {
                realDamager = (LivingEntity) damager;
                damagerModifier = AttributeTracker.trackModifier((LivingEntity) damager);
                if(damager instanceof Player) {
                    attackCooldown = AttackIndicator.getCurrentCharge((Player) damager);
                }
            } else if(damager instanceof Projectile) {
                Projectile projectile = (Projectile) damager;
                arrow = projectile;
                if(projectile.hasMetadata(META_DAMAGE_MODIFIER)){
                    damagerModifier = (Map<String, Double>) projectile.getMetadata(META_DAMAGE_MODIFIER).get(0).value();
                }else {
                    damagerModifier = AttributeTracker.trackModifier((LivingEntity) projectile.getShooter());
                }
                if(projectile.hasMetadata(META_ARROW_FORCE)) damageMultiplier = projectile.getMetadata(META_ARROW_FORCE).get(0).asFloat();

                if(projectile.getShooter() instanceof LivingEntity) {
                    realDamager = (LivingEntity) projectile.getShooter();
                }
            }
        }
        if(realDamager != null){
            KanskyUtil.clearAttackSpeed(realDamager);
        }
        //set damage
        damage = KanskyUtil.getDamage(livEntityModifier,damagerModifier);
        finalDamage = damage * damageMultiplier * attackCooldown;
        event.setDamage(finalDamage);
        KanDamageEvent callEvent = new KanDamageEvent(livEntity,realDamager,arrow,finalDamage);
        Bukkit.getPluginManager().callEvent(callEvent);
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void printDamage(EntityDamageEvent event) {
        if(!Config.isDebug) return;
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            Player player = (Player)entity;
            player.sendMessage("Raw:"+ event.getDamage());
            player.sendMessage("Final:"+ event.getFinalDamage());
            player.sendMessage("Health:"+ (player.getHealth() - event.getFinalDamage()));
        }
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent byEntity = (EntityDamageByEntityEvent)event;
            Entity damager = byEntity.getDamager() instanceof Projectile
                    && ((Projectile) byEntity.getDamager()).getShooter() instanceof Entity ?
                    (Entity) ((Projectile) byEntity.getDamager()).getShooter()
                    : byEntity.getDamager();
            damager.sendMessage("ARaw:" + event.getDamage());
            damager.sendMessage("AFinal:" + event.getFinalDamage());
            if(event.getEntity() instanceof LivingEntity) {
                damager.sendMessage("AHealth:" + (((LivingEntity) event.getEntity()).getHealth() - event.getFinalDamage()));
                if(damager instanceof Player) {
                    damager.sendMessage("Your attack cooldown:" +
                            AttackIndicator.getCurrentCharge((Player) damager));
                    damager.sendMessage("Your last attack:" +
                            AttackIndicator.getLastAttackTime((Player) damager));
                    damager.sendMessage("Your PassedTick:" +
                            AttackIndicator.getPassedTick((Player) damager));
                    damager.sendMessage("Your MaximumRechargeTime:" +
                            AttackIndicator.getMaximumRechargeTime((Player) damager));
                }
            }
        }
    }

}
