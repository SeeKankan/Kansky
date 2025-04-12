package io.seekankan.github.kansky.mythicmobs.listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.seekankan.github.kansky.inventory.ForgeMainGUIHolder;
import io.seekankan.github.kansky.inventory.SlayerGUIHolder;
import io.seekankan.github.kansky.kanattribute.DefaultItem;
import io.seekankan.github.kansky.mythicmobs.SlayerInstance;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MythicMobListener implements Listener {
    /**
     * KanDrops:
     *  - def_name count chance
     *  - def_name min(inclusive)-max(inclusive) chance
     * @param event
     */
    @EventHandler(priority = EventPriority.LOW,ignoreCancelled = true)
    public void addLoot(MythicMobDeathEvent event) {
        List<String> config = event.getMobType().getConfig().getStringList("KanDrops");
        if(config == null) return;
        for(String str : config) {
            String[] subConfig = str.split(" ");
            String defName = subConfig[0];
            int count;
            double chance = Double.parseDouble(subConfig[2]);
            if(Math.random() > chance) continue;
            try {
                count = Integer.parseInt(subConfig[1]);
            } catch(NumberFormatException ignored) {
                int[] countConfig = Arrays.stream(subConfig[1].split("-")).mapToInt(Integer::parseInt).toArray();
                count = KanskyUtil.RANDOM.nextInt(countConfig[0],++countConfig[1]);
            }
            ItemStack itemStack = DefaultItem.createItem(defName);
            itemStack.setAmount(count);
            event.getDrops().add(itemStack);
        }

    }

    /**
     * KanSummonSlayer: slayer exp
     * KanSummonSlayers:
     *  - slayer exp
     *  - slayer exp
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void addSlayerExp(MythicMobDeathEvent event) {
        if(!(event.getKiller() instanceof Player)) return;
        Player player = (Player) event.getKiller();
        SlayerInstance slayer = new SlayerInstance(player);
        String config = event.getMobType().getConfig().getString("KanSummonSlayer");
        String addSlayerName = slayer.getSlayerName();
        if(config != null) {
            String[] subConfig = config.split(" ");
            String slayerName = subConfig[0];
            String exp = subConfig[1];
            if (Objects.equals(slayerName, addSlayerName)) {
                slayer.addSlayerExp(event.getEntity().getLocation(), Integer.parseInt(exp));
            }
        }
        List<String> configs = event.getMobType().getConfig().getStringList("KanSummonSlayers");
        if(configs != null) {
            for (String strConfig : configs) {
                String[] subConfig = strConfig.split(" ");
                String slayerName = subConfig[0];
                String exp = subConfig[1];
                if (Objects.equals(slayerName, addSlayerName)) {
                    slayer.addSlayerExp(event.getEntity().getLocation(), Integer.parseInt(exp));
                    break;
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void onClickSlayerGUI(InventoryClickEvent event) {
        KanskyUtil.filterInventory(event, SlayerGUIHolder.class);
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void onDragSlayerGUI(InventoryDragEvent event) {
        if(event.getInventory().getHolder() instanceof SlayerGUIHolder) event.setCancelled(true);
    }
}
