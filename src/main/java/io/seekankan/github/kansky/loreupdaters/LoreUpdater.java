package io.seekankan.github.kansky.loreupdaters;

import io.seekankan.github.kansky.kanattribute.AttributeFormat;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.kanattribute.Slot;
import io.seekankan.github.kansky.util.KanskyUtil;
import io.seekankan.github.kanutil.util.Maps;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.BiFunction;

public class LoreUpdater {
    private static YamlConfiguration loreUpdaterYAML;

    private static final HashMap<String, BiFunction<Player,ItemStack,List<String>>> subLoreMap = Maps.of(
            "{attributes}",LoreGetters.ATTRIBUTES_LORE_GETTER,
            "{info}",LoreGetters.INFO_LORE_GETTER,
            "{rarity}",LoreGetters.RARITY_LORE_GETTER
    );

    public static void registerLoreGetter(String namespace,BiFunction<Player,ItemStack,List<String>> getter) {
        subLoreMap.put(namespace,getter);
    }
    public static void unregisterLoreGetter(String namespace) {
        subLoreMap.remove(namespace);
    }
    
    public static void loadLoreUpdater() {
        loreUpdaterYAML = KanskyUtil.getConfig("lore_updaters.yml");
    }
//    static YamlConfiguration getConfig() {
//        return loreUpdaterYAML;
//    }

    public static List<String> getNewLore(Player player, ItemStack itemStack) {
        ItemConfig itemConfig = ItemConfig.getItemConfig(itemStack);
        if(itemConfig == null) {
            return itemStack.getItemMeta().getLore();
        }
        List<String> template = loreUpdaterYAML.getStringList("lore_config");
        List<String> lore = new ArrayList<>();
//        List<String> attribute = new ArrayList<>();
//        List<String> info = itemConfig.info;
//        for(Slot slot : Slot.values()) {
//            Map<String,Double> attributeMap = itemConfig.getDamageModifier(slot);
//            if(attributeMap.isEmpty()) continue;
//            attribute.add(slot.getFormat() + ": ");
//            attributeMap.forEach((str,value) -> {
//                String attrName = AttributeFormat.format(str);
//                attribute.add(attrName + ": " + value.longValue());
//            });
//        }
//        String rarity = itemConfig.rarity.getRarityName();
//        template.forEach(str -> {
//            switch(str) {
//                case "{attributes}":
//                    lore.addAll(attribute);
//                    break;
//                case "{info}":
//                    lore.addAll(info);
//                    break;
//                case "{rarity}":
//                    lore.add(rarity);
//                    break;
//                default:
//                    lore.add(str);
//                    break;
//            }
//        });
        template.forEach(str -> {
            BiFunction<Player,ItemStack,List<String>> getter = subLoreMap.get(str);
            if(getter == null) {
                lore.add(str);
                return;
            }
            lore.addAll(getter.apply(player, itemStack));
        });
        return lore;
    }

    public static YamlConfiguration getLoreUpdaterYAML() {
        return loreUpdaterYAML;
    }
}
