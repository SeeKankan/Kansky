package io.seekankan.github.kansky.util;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.listener.AttackCooldownChangeListener;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;


public class AttackIndicator {

//    public static float getAttackCooldown(HumanEntity human) {
//        CraftHumanEntity cHuman = (CraftHumanEntity) human;
//        EntityHuman nmsHuman = cHuman.getHandle();
//        Field field = null;
//        try {
//            field = EntityLiving.class.getDeclaredField("aE");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        field.setAccessible(true);
//        try {
//            System.out.println("aE:"+field.get(nmsHuman));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        System.out.println("dr:"+nmsHuman.dr());
//        return nmsHuman.n(0);
//    }

    public static long getLastAttackTime(Player player) {
        Long rawTime = AttackCooldownChangeListener.lastAttack.get(player.getUniqueId());
        return rawTime != null ? rawTime : 0;
    }
    public static long getPassedTick(Player player) {
        return KanskyUtil.toGameTick(System.currentTimeMillis()) - getLastAttackTime(player);
    }
    public static void resetAttackCooldown(Player player) {
        AttackCooldownChangeListener.lastAttack.remove(player.getUniqueId());
    }
    public static float getMaximumRechargeTime(Player player) {
        double attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
        return (float)(20D / attackSpeed);
    }
    public static float getCurrentCharge(Player player) {
        return getCurrentCharge(player, 0);
    }
    public static float getCurrentCharge(Player player, int offset) {
        float maxRechargeTime = getMaximumRechargeTime(player);
        return KanskyUtil.minMax(Math.min(getPassedTick(player) + offset,maxRechargeTime) / maxRechargeTime,0F,1F);
    }

}
