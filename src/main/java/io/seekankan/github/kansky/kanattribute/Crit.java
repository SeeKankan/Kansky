package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.Config;

import java.util.Map;

public class Crit implements DamageModifier{

    private Crit() {}
    private volatile static Crit instance;

    public static Crit getInstance() {
        if(instance == null){
            synchronized (Crit.class) {
                if(instance == null) {
                    instance =  new Crit();
                }
            }
        }
        return instance;
    }

    @Override
    public double handleDamage(double damage, Map<String,Double> modifier) {
        boolean isCrit = Math.random() < (Config.critChanceAdd + modifier.getOrDefault("critchance",0d));
        double critDamage = Math.max(modifier.getOrDefault("critdamage",0d) / Config.critDamageDivision,Config.minCritDamage);
        critDamage = critDamage + Config.critDamageAdd;
        return isCrit ? damage * critDamage : damage;
    }

    @Override
    public DamageModifierType getType() {
        return DamageModifierType.IN_ATTACKER;
    }
}