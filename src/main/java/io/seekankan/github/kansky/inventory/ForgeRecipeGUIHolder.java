package io.seekankan.github.kansky.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ForgeRecipeGUIHolder implements InventoryHolder {
    private final int slot;
    private final int page;

    public ForgeRecipeGUIHolder(int slot, int page) {
        this.slot = slot;
        this.page = page;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public int getSlot() {
        return slot;
    }

    public int getPage() {
        return page;
    }
}
