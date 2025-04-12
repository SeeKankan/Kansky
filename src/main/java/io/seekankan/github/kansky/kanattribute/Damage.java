package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.Config;

import java.util.Map;

public class Damage implements DamageModifier {

    private Damage() {}
    private volatile static Damage instance;

    public static Damage getInstance() {
        if(instance == null){
            synchronized (Damage.class) {
                if(instance == null) {
                    instance =  new Damage();
                }
            }
        }
        return instance;
    }

    @Override
    public double handleDamage(double damage,Map<String,Double> modifier) {
        return Math.max(modifier.getOrDefault("damage",0d), Config.minDamage);
    }

    @Override
    public DamageModifierType getType() {
        return DamageModifierType.IN_ATTACKER;
    }


}
