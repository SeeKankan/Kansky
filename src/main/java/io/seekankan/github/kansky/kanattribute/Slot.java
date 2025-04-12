package io.seekankan.github.kansky.kanattribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Slot {
    MAINHAND,
    OFFHAND,
    HEAD,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    INVENTORY,
    ACCESSORY;
    public static List<Slot> valuesOf(List<String> values){
        ArrayList<Slot> array = new ArrayList<>(values.size());
        for(String value : values){
            array.add(valueOf(value));
        }
        return array;
    }
}
