package io.seekankan.github.kansky.listener;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.forge.*;
import io.seekankan.github.kansky.inventory.ForgeRecipeGUIHolder;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ForgeRecipeGUIListener implements Listener {
    @EventHandler(priority = EventPriority.LOW,ignoreCancelled = true)
    public void handleClick(InventoryClickEvent event){
        if(KanskyUtil.filterInventory(event, ForgeRecipeGUIHolder.class)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ForgeRecipeGUIHolder holder = (ForgeRecipeGUIHolder) inventory.getHolder();
        ItemStack itemStack = inventory.getItem(event.getSlot());
        if(!KanskyUtil.isItemStack(itemStack)) return;
        switch(ForgeRecipeGUIConfig.getAction(itemStack)){
            case PREVIOUS_PAGE:
                Forge.openRecipeGUI(player,holder.getSlot(),holder.getPage() - 1);
                break;
            case NEXT_PAGE:
                Forge.openRecipeGUI(player,holder.getSlot(),holder.getPage() + 1);
                break;
            case SELECT:
                ForgeMeta oldForgeMeta = ForgeDataUtil.getForgeMeta(player, holder.getSlot());
                if(oldForgeMeta.getForgeItem() == null) {
                    String strForge = ForgeRecipeGUIConfig.getForge(itemStack);
                    ForgeItem forgeItem = Forge.getForgeConfig().getRecipe().get(strForge);
                    ItemStack[] deleteItem = KanskyUtil.deleteKanItem(player,forgeItem);
                    if(deleteItem != null) {
                        ForgeDataUtil.setForgeMeta(player,holder.getSlot(),new ForgeMeta(strForge,deleteItem));
                        Forge.openMainForgeGUI(player);
                    } else {
                        player.sendMessage(Message.FORGE__NOT_ENOUGH_MATERIALS.getMessage());
                    }
                }
                break;
        }
    }
}
