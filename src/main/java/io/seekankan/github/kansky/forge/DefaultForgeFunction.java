package io.seekankan.github.kansky.forge;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DefaultForgeFunction {
    public static void loadDefForgeFunction() {
        ForgeFunctionRegistry.registerFunction("MergeNBT",DefaultForgeFunction::mergeNBT);
    }
    public static void mergeNBT(ItemStack itemStack, List<ItemStack> items, List<String> args) {
        String mergeItemId = args.get(0);
        for(ItemStack rawItem : items) {
            String id = ItemConfig.getKanskyItemId(rawItem);
            if(mergeItemId.equals(id)) {
                NBT.modify(itemStack,itemNBT -> {
                    NBT.modify(rawItem.clone(),nbt -> {
                        nbt.mergeCompound(itemNBT);
                        itemNBT.clearNBT();
                        itemNBT.mergeCompound(nbt);
                    });
                });
                break;
            }
        }
    }
}
