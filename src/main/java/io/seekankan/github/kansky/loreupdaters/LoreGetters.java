package io.seekankan.github.kansky.loreupdaters;

import io.seekankan.github.kansky.kanattribute.AttributeFormat;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.kanattribute.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class LoreGetters {
    public static final BiFunction<Player, ItemStack, List<String>> ATTRIBUTES_LORE_GETTER = (player, itemStack) -> {
        ItemConfig itemConfig = ItemConfig.getItemConfig(itemStack);
        if(itemConfig == null) {
            return Collections.emptyList();
        }
        List<String> attribute = new ArrayList<>();
        for(Slot slot : Slot.values()) {
            Map<String,Double> attributeMap = itemConfig.getDamageModifier(slot);
            if(attributeMap.isEmpty()) continue;
            attribute.add(slot.getFormat() + ": ");
            attributeMap.forEach((str,value) -> {
                String attrName = AttributeFormat.format(str);
                attribute.add(attrName + ": " + value.longValue());
            });
        }
        return attribute;
    };
    public static final BiFunction<Player, ItemStack, List<String>> INFO_LORE_GETTER = (player, itemStack) -> {
        ItemConfig itemConfig = ItemConfig.getItemConfig(itemStack);
        if(itemConfig == null) {
            return Collections.emptyList();
        }
        return itemConfig.info;
    };
    public static final BiFunction<Player, ItemStack, List<String>> RARITY_LORE_GETTER = (player, itemStack) -> {
        ItemConfig itemConfig = ItemConfig.getItemConfig(itemStack);
        if(itemConfig == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(itemConfig.rarity.getRarityName());
    };
}
