package io.seekankan.github.kansky.forge;

import org.bukkit.inventory.ItemStack;

import java.util.List;

@FunctionalInterface
public interface ForgeFunction {
    void handleItemStack(ItemStack itemStack, List<ItemStack> items,List<String> args);
}
