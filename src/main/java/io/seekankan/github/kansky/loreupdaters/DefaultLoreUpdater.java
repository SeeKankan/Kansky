package io.seekankan.github.kansky.loreupdaters;

import io.seekankan.github.kansky.kanattribute.AttributeFormat;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.kanattribute.Slot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultLoreUpdater /* implements LoreUpdater */{
//    private final ConfigurationSection config;
//    public DefaultLoreUpdater(ConfigurationSection config) {
//        this.config = config;
//    }
//
//    /**
//     *
//     * @param player 玩家
//     * @param itemStack 要被更新的物品
//     * @return 不能为null
//     */
//    @Override
//    public List<String> getNewLore(Player player, ItemStack itemStack) {
//        ItemConfig itemConfig = ItemConfig.getItemConfig(itemStack);
//        if(itemConfig == null) {
//            return itemStack.getItemMeta().getLore();
//        }
//        List<String> template = config.getStringList("lore_config");
//        List<String> lore = new ArrayList<>();
//        List<String> attribute = new ArrayList<>();
//        List<String> info = itemConfig.info;
//        for(Slot slot : Slot.values()) {
//            Map<String,Double> attributeMap = itemConfig.getDamageModifier(slot);
//            if(attributeMap.isEmpty()) continue;
//            attribute.add(slot.getFormat());
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
//                case "#{info}":
//                    lore.addAll(info);
//                case "{rarity}":
//                    lore.add(rarity);
//            }
//        });
//        return lore;
//    }
}
