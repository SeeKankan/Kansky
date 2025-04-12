package io.seekankan.github.kansky.kanattribute;


import java.util.Map;

public interface DamageModifier {
    double handleDamage(double damage, Map<String,Double> modifier);
    DamageModifierType getType();
}
