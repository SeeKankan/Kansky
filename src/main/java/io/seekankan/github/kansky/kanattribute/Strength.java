package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.Config;

import java.util.Map;

public class Strength implements DamageModifier{

    private Strength() {}
    private volatile static Strength instance;

    public static Strength getInstance() {
        if(instance == null){
            synchronized (Strength.class) {
                if(instance == null) {
                    instance =  new Strength();
                }
            }
        }
        return instance;
    }

    @Override
    public double handleDamage(double damage, Map<String,Double> modifier) {
        // x * 0 = 0 ; x * 1 = x
        return damage *
                (Config.strengthAdd +
                (Math.max(modifier.getOrDefault("strength",0d),Config.minStrength) / Config.strengthDivision));
    }

    @Override
    public DamageModifierType getType() {
        return DamageModifierType.IN_ATTACKER;
    }
}
