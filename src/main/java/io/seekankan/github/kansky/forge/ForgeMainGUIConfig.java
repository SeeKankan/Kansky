package io.seekankan.github.kansky.forge;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.inventory.ForgeMainGUIHolder;
import io.seekankan.github.kansky.util.ItemCreator;
import io.seekankan.github.kansky.util.ItemStackNBTProxy;
import io.seekankan.github.kansky.util.KanskyUtil;
import io.seekankan.github.kanutil.string.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ForgeMainGUIConfig {
//    public static final String MARK_FORGE_SLOT = "markforgeslot";

    private int size;
    private Map<Integer, Material> forgeSlotMap;
    // 10 -> 0 11 -> 1
    private Map<Integer,Integer> slotLocation;
    private ItemStack fill;
    private String title;
//    private List<String> rawLore;
//    private List<String> noForgeRawLore;
    private Placeholder[] rawLorePH;
    private Placeholder[] noForgeRawLorePH;

    public ForgeMainGUIConfig(int size, Map<Integer, Material> forgeSlot, Map<Integer, Integer> forgeSlots, ItemStack fill, String title, List<String> rawLore, List<String> noForgeRawLore) {
        this.size = size;
        this.forgeSlotMap = forgeSlot;
        this.slotLocation = forgeSlots;
        this.fill = fill;
        this.title = title;
//        this.rawLore = rawLore;
//        this.noForgeRawLore = noForgeRawLore;
        this.rawLorePH = new Placeholder[rawLore.size()];
        this.noForgeRawLorePH = new Placeholder[noForgeRawLore.size()];
        for(int i = 0;i < rawLorePH.length;i++) {
            rawLorePH[i] = new Placeholder(rawLore.get(i),'{','}');
        }
        for(int i = 0;i < noForgeRawLorePH.length;i++) {
            noForgeRawLorePH[i] = new Placeholder(noForgeRawLore.get(i),'{','}');
        }
    }

    public static ForgeMainGUIConfig resolveConfig(ConfigurationSection config){
        int size = config.getInt("size");
        ConfigurationSection menuConfig = config.getConfigurationSection("menu");
        Map<Integer,Material> forgeSlot = new HashMap<>();
        Map<Integer,Integer> slotLocation = new HashMap<>();
        for(String key : menuConfig.getKeys(false)){
            ConfigurationSection section = menuConfig.getConfigurationSection(key);
            switch (section.getString("type")){
                case "FORGE_SLOT":
                    forgeSlot.put(Integer.parseInt(key),Material.valueOf(section.getString("normal_material")));
                    slotLocation.put(Integer.parseInt(key),section.getInt("slot"));
            }
        }
        ItemStack fill = new ItemStack(Material.valueOf(config.getString("fill")));
        String title = config.getString("title");
        List<String> rawLore = config.getStringList("forge_slot_lore");
        List<String> noForgeRawLore = config.getStringList("forge_slot_default_lore");
        return new ForgeMainGUIConfig(size,forgeSlot,slotLocation,fill,title,rawLore,noForgeRawLore);
    }
    public void refreshInventory(Player player,Inventory inv) {
        for(int loc : forgeSlotMap.keySet()){
            int forgeIndex = slotLocation.get(loc);
            ForgeMeta forgeMeta = ForgeDataUtil.getForgeMeta(player,forgeIndex);
            List<String> lore = getLore(forgeIndex,forgeMeta);
            ItemStack itemStack = new ItemCreator(forgeSlotMap.get(loc)).lore(lore).create();
            NBT.modify(itemStack, ItemStackNBTProxy.class, nbt -> {
                nbt.setMarkForgeSlot(forgeIndex);
//                nbt.setInteger(MARK_FORGE_SLOT,forgeIndex);
            });
            inv.setItem(loc,itemStack);
        }
        for(int index = 0;index < size;index++){
            if(!KanskyUtil.isItemStack(inv.getItem(index))){
                inv.setItem(index,fill);
            }
        }
    }
    public Inventory createGUI(Player player){
        Inventory inv = Bukkit.createInventory(new ForgeMainGUIHolder(),size,title);
        refreshInventory(player,inv);
        return inv;
    }
    public static Optional<Integer> getForgeSlotByItem(ItemStack itemStack){
        return NBT.modify(itemStack,ItemStackNBTProxy.class, proxy -> {
//            return nbt.hasTag(MARK_FORGE_SLOT) ?
//                    nbt.getInteger(MARK_FORGE_SLOT) : null;
            return Optional.ofNullable(proxy.hasMarkForgeSlot() ? proxy.getMarkForgeSlot() : null);
        });
    }
    private List<String> getDefFormatLore(String slot){
        ArrayList<String> lore = new ArrayList<>(noForgeRawLorePH.length);
        for (Placeholder ph : noForgeRawLorePH) {
            lore.add(formatString(ph, slot, "{item}", "{time}"));
        }
        return lore;
    }
    private List<String> getFormatLore(String slot,String item,String time){
        ArrayList<String> lore = new ArrayList<>(rawLorePH.length);
        for (Placeholder ph : rawLorePH) {
            lore.add(formatString(ph, slot, item, time));
        }
        return lore;
    }
    private static String formatString(Placeholder ph, String slot, String item, String time){
        return ph.get(new HashMap<String,String>(){{
            put("slot",slot);
            put("item",item);
            put("time",time);
        }});
    }
    public List<String> getLore(int slot,ForgeMeta meta) {
        if(meta.getForgeItem() == null){
            return getDefFormatLore(String.valueOf(slot));
        } else{
            long displayTime = ForgeDataUtil.getEndTime(meta);
            String formatTime;
            if(displayTime > 0) {
                formatTime = KanskyUtil.timeToHMS(displayTime);
            } else {
                formatTime = Message.FORGE__FINISH.getMessage();
            }
//            formatTime = displayTime > 0 ? timeFormatter.format(displayTime) : Message.FORGE__FINISH.getMessage();
            return getFormatLore(String.valueOf(slot),meta.getItem(),formatTime);
        }
    }
}
