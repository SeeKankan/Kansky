package io.seekankan.github.kansky.loreupdaters;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

import java.util.List;

public interface LoreUpdater {
    @Contract(pure = true)
    List<String> getNewLore(Player player, ItemStack itemStack);
}
