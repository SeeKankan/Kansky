package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.Config;

import java.util.Map;

public class Defense implements DamageModifier {

    private Defense() {}
    private volatile static Defense instance;

    public static Defense getInstance() {
        if(instance == null){
            synchronized (Defense.class) {
                if(instance == null) {
                    instance =  new Defense();
                }
            }
        }
        return instance;
    }

    @Override
    public double handleDamage(double damage, Map<String,Double> modifier) {
        double defense = Math.max(modifier.getOrDefault("defense",0d), Config.minDefense);
        return damage - damage * (defense / (defense + Config.defenseDecrease));
    }

    @Override
    public DamageModifierType getType() {
        return DamageModifierType.IN_ATTACKER;
    }


}
