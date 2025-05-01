package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;

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
    private static YamlConfiguration slotNameYAML;
    public static void loadSlotName() {
        slotNameYAML = KanskyUtil.getConfig("attribute_name.yml");
    }
    public static List<Slot> valuesOf(List<String> values){
        ArrayList<Slot> array = new ArrayList<>(values.size());
        for(String value : values){
            array.add(valueOf(value));
        }
        return array;
    }
    public String getFormat() {
        return slotNameYAML.getString(name(),"In " + name());
    }
}
