package io.seekankan.github.kansky.loreupdaters;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DefaultLoreUpdater implements LoreUpdater{
    private final ConfigurationSection config;
    public DefaultLoreUpdater(ConfigurationSection config) {
        this.config = config;
    }
    @Override
    public List<String> getNewLore(Player player, ItemStack itemStack) {
        return null;
    }
}
