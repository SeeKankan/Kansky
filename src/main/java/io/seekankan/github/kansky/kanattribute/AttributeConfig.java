package io.seekankan.github.kansky.kanattribute;

import java.util.List;
import java.util.Objects;

//    public static final String KANSKY_ITEM_ID = "kanskyitemid";
public class AttributeConfig {
    private final String damageModifier;
    private final List<Slot> slots;
    private final double value;
    private int hash = 0;

    public AttributeConfig(String damageModifier, List<Slot> slots, double value) {
        this.damageModifier = damageModifier;
        this.slots = slots;
        this.value = value;
    }

    public String getDamageModifier() {
        return damageModifier;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeConfig)) return false;
        AttributeConfig that = (AttributeConfig) o;
        return Double.compare(that.value, value) == 0 && Objects.equals(damageModifier, that.damageModifier) && Objects.equals(slots, that.slots);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(damageModifier, slots, value);
            return hash;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "AttributeConfig{" +
                "damageModifier='" + damageModifier + '\'' +
                ", slots=" + slots +
                ", value=" + value +
                '}';
    }
}
