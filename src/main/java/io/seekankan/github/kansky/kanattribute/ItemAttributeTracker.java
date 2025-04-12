package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ItemAttributeTracker implements AttributeTracker{
    @Override
    public Map<String, Double> getModifier(LivingEntity entity) {
        Map<Slot, List<ItemStack>> slots = KanskyUtil.getSlotFromEquipment(entity.getEquipment());
        LinkedList<Map<String, Double>> maps = new LinkedList<>();
        for(Map.Entry<Slot,List<ItemStack>> entry: slots.entrySet()){
            Slot slot = entry.getKey();
            List<ItemStack> items = entry.getValue();
            for(ItemStack item : items){
                if(!KanskyUtil.isItemStack(item)) continue;
                ItemConfig itemConfig = ItemConfig.getItemConfig(item);
                if(itemConfig != null) {
                    maps.add(itemConfig.getDamageModifier(slot));
                }
            }
        }
        HashMap<String, Double> modifierMap = new HashMap<>();
        KanskyUtil.mergeMapByPlus(modifierMap,maps);
        return modifierMap;
    }

}
