package io.seekankan.github.kansky.kanattribute;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DefaultAttributeTracker implements AttributeTracker{
    @Override
    public Map<String, Double> getModifier(@NotNull LivingEntity entity) {
        EntityEquipment eq = entity.getEquipment();
        ItemStack[] items = KanskyUtil.getItemsFromEquipment(eq);
        ReadableNBT[] nbts = new ReadableNBT[items.length + 1];
        KanskyUtil.getNBTs(nbts,items);
        nbts[items.length] = new NBTEntity(entity);
        Map<String,Double>[] maps = new Map[nbts.length];
        int mapsIndex = 0;
        for(ReadableNBT nbt : nbts){
            maps[mapsIndex] = getModifier(nbt);
            mapsIndex++;
        }
        Map<String,Double> modifierMap = new HashMap<>();
        KanskyUtil.mergeMapByPlus(modifierMap,maps);
        return modifierMap;
    }

    /**
     *
     * @param nbt
     * @return A map that have damage modifier num.
     *
     * nbt:
     * {
     *     kansky:{
     *         namespace:{
     *             modifierId: value
     *             modifierId2: value
     *         }
     *         ...
     *     }
     * }
     */
    public static @NotNull Map<String,Double> getModifier(ReadableNBT nbt) {
        Map<String,Double> modifier = new HashMap<>();
        if(nbt == null) return modifier;
        ReadableNBT kansky = nbt.getCompound("kansky");
        if(kansky == null) return modifier;
        kansky.getKeys().forEach(name-> {
            ReadableNBT namespace = kansky.getCompound(name);
            if(namespace == null) return;
            namespace.getKeys().forEach(key ->
                    modifier.put(key,modifier
                            .getOrDefault(key,0d)
                            + namespace.getOrDefault(key,0d)));
        });
        return modifier;

    }

}
