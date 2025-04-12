package io.seekankan.github.kansky.kanattribute;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class ItemConfig implements ConfigurationSerializable {
    private static YamlConfiguration itemYAML;
    private static final Map<String,ItemConfig> itemConfigMap = new HashMap<>(); //all itemConfig will register at there
    public static final String KANSKY_ITEM_ID = "kanskyitemid";
    public static class AttributeConfig {
        public String damageModifier;
        public List<Slot> slots;
        public double value;

        public AttributeConfig(String damageModifier, List<Slot> slots, double value) {
            this.damageModifier = damageModifier;
            this.slots = slots;
            this.value = value;
        }

        @Override
        public String toString() {
            return "AttributeConfig{" +
                    "damageModifier='" + damageModifier + '\'' +
                    ", slots=" + slots +
                    ", value=" + value +
                    '}';
        }
    }
    public AttributeConfig[] attributes; //damage modifier cannot dupe
    public String displayName;
    //maybe singleton
    protected ItemConfig(AttributeConfig[] attributes,String displayName){
        this.attributes = attributes;
        this.displayName = displayName;
    }
    //{===io.seekankan.github.kansky.kanattribute.ItemConfig, attribute={damage={slot=[MAINHAND], value=100}}}
    @Contract("_ -> new")
    public static @NotNull ItemConfig deserialize(@NotNull Map<String,Object> map) {
        Map attrsMap = (Map) map.getOrDefault("attribute",new HashMap<>());
        LinkedList<AttributeConfig> attributeConfig = new LinkedList<>();
        for(Object key : attrsMap.keySet()) {
            String attrName = (String) key;
            Map attrConfMap = (Map) attrsMap.get(key);
            List<Slot> slots = Slot.valuesOf((List<String>) attrConfMap.get("slot"));
            double value = Double.parseDouble(attrConfMap.get("value").toString());
            attributeConfig.add(new AttributeConfig(attrName,slots,value));
        }
        String displayName = map.getOrDefault("display_name","UNNAME").toString();
        return new ItemConfig(attributeConfig.toArray(new AttributeConfig[0]),displayName);
    }
    @Override
    public Map<String, Object> serialize() {
        throw new RuntimeException("You shouldn't write item config to yaml!This isn't support,sorry.");
    }

    public Map<String,Double> getDamageModifier(Slot slot){
        HashMap<String,Double> damageModifier = new HashMap<>();
        for(AttributeConfig attrConf : attributes) {
//            for(Slot forSlot : attrConf.slots){
//                if(forSlot == slot){
//                    damageModifier.put(attrConf.damageModifier, attrConf.value);
//                }
//            }
            if(attrConf.slots.contains(slot)) {
                damageModifier.put(attrConf.damageModifier, attrConf.value);
            }
        }
        return damageModifier;
    }

    public static void loadItemConfig() {
        itemConfigMap.clear(); //clear old item config
        ConfigurationSerialization.registerClass(ItemConfig.class);
        Kansky instance = Kansky.getInstance();
        File file = new File(instance.getDataFolder(),"item.yml");
        if(!file.exists()) {
            instance.getLogger().info("Create item.yml");
            instance.saveResource("item.yml",true);
        }
        itemYAML = YamlConfiguration.loadConfiguration(file);
        Set<String> keys = itemYAML.getKeys(false);
        keys.forEach(key -> itemConfigMap.put(key, (ItemConfig) itemYAML.get(key)));
    }
    public static String getKanskyItemId(ItemStack itemStack) {
        return NBT.get(itemStack, nbt -> {
            return nbt.getString(KANSKY_ITEM_ID);
        });
    }
    public static @Nullable ItemConfig getItemConfig(@Nullable String itemId){
        return itemConfigMap.get(itemId);
    }
    /**
     * nbt:
     * {
     *     kanskyitemid: "some id" //kanskyitem id
     * }
     * @param nbt
     * @return a item config.
     */
    public static @Nullable ItemConfig getItemConfig(@Nullable ReadableNBT nbt){
        if(nbt == null) return null;
        String itemId = nbt.getString(KANSKY_ITEM_ID);
        return itemId != null ? getItemConfig(itemId) : null;
    }
    public static @Nullable ItemConfig getItemConfig(ItemStack itemStack){
        if(!KanskyUtil.isItemStack(itemStack)) return null;
        return getItemConfig(NBT.readNbt(itemStack));
    }
    public static boolean is(ItemStack itemStack,String itemId){
        return itemConfigMap.get(itemId) == getItemConfig(itemStack);
    }
}
