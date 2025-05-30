package io.seekankan.github.kansky.kanattribute;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.util.ItemStackNBTProxy;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemConfig implements ConfigurationSerializable {
    private static YamlConfiguration itemYAML;
    private static final Map<String,ItemConfig> itemConfigMap = new HashMap<>(); //all itemConfig will register at there
    public AttributeConfig[] attributes; //damage modifier cannot dupe
    public String displayName;
    public Rarity rarity;
    public List<String> info;
    //maybe singleton
    protected ItemConfig(AttributeConfig[] attributes,String displayName,Rarity rarity,List<String> info){
        this.attributes = attributes;
        this.displayName = displayName;
        this.rarity = rarity;
        this.info = info;
    }
    //{===io.seekankan.github.kansky.kanattribute.ItemConfig, attribute={damage={slot=[MAINHAND], value=100}}}
    @Contract("_ -> new")
    @SuppressWarnings({"unused","unchecked"})
    public static @NotNull ItemConfig deserialize(@NotNull Map<String,Object> map) {
//        @SuppressWarnings("unchecked")
//        Map<String, Object> attrsMap = Maps.checkType(
//                (Map<String, Object>) map.getOrDefault("attribute",new HashMap<>()),
//                String.class,
//                Object.class
//        );
        Map<String, Object> attrsMap = (Map<String, Object>) map.getOrDefault("attribute",new HashMap<>());
        LinkedList<AttributeConfig> attributeConfig = new LinkedList<>();
        for(String key : attrsMap.keySet()) {
            Map<String,Object> attrConfMap = (Map<String,Object>) attrsMap.get(key);
            List<Slot> slots = Slot.valuesOf((List<String>) attrConfMap.get("slot"));
            double value = Double.parseDouble(attrConfMap.get("value").toString());
            attributeConfig.add(new AttributeConfig(key,slots,value));
        }
        String displayName = Objects.requireNonNull(map.get("display_name"),"Item display name cannot be null!").toString();
        Rarity rarity = Rarity.getRarity((Integer) map.getOrDefault("rarity","1"));
        List<String> info = Collections.unmodifiableList(
                (List<String>) map.getOrDefault("info",Collections.emptyList())
        );
        return new ItemConfig(attributeConfig.toArray(new AttributeConfig[0]),displayName,rarity,info);
    }
    @Override
    public Map<String, Object> serialize() {
        return new HashMap<String, Object>(){{
            put("attribute",new HashMap<String, Object>(){{
                for(AttributeConfig attribute : attributes) {
                    put(attribute.getDamageModifier(),new HashMap<String, Object>(){{
                        put("slot", attribute.getSlots());
                        put("value",attribute.getValue());
                    }});
                }
            }});
            put("display_name",displayName);
            put("rarity",rarity.getId());
            put("info",info);
        }};
    }

    public Map<String,Double> getDamageModifier(Slot slot){
        HashMap<String,Double> damageModifier = new HashMap<>();
        for(AttributeConfig attrConf : attributes) {
//            for(Slot forSlot : attrConf.slots){
//                if(forSlot == slot){
//                    damageModifier.put(attrConf.damageModifier, attrConf.value);
//                }
//            }
            if(attrConf.getSlots().contains(slot)) {
                damageModifier.put(attrConf.getDamageModifier(), attrConf.getValue());
            }
        }
        return damageModifier;
    }

    public static void loadItemConfig() {
        itemConfigMap.clear(); //clear old item config
        ConfigurationSerialization.registerClass(ItemConfig.class);
//        Kansky instance = Kansky.getInstance();
//        File file = new File(instance.getDataFolder(),"item.yml");
//        if(!file.exists()) {
//            instance.getLogger().info("Create item.yml");
//            instance.saveResource("item.yml",true);
//        }
        itemYAML = KanskyUtil.getConfig("item.yml"); //YamlConfiguration.loadConfiguration(file);
        Set<String> keys = itemYAML.getKeys(false);
        keys.forEach(key -> itemConfigMap.put(key, (ItemConfig) itemYAML.get(key)));
    }
    public static String getKanskyItemId(ItemStack itemStack) {
        //            return nbt.getString(KANSKY_ITEM_ID);
        return NBT.modify(itemStack, ItemStackNBTProxy.class, ItemStackNBTProxy::getKanskyItemId);
    }
    public static @Nullable ItemConfig getItemConfig(@Nullable String itemId){
        return itemConfigMap.get(itemId);
    }

//    /**
//     * nbt:
//     * {
//     *     kanskyitemid: "some id" //kanskyitem id
//     * }
//     * @param nbt
//     * @return an item config.
//     */
//    public static @Nullable ItemConfig getItemConfig(@Nullable ReadableNBT nbt){
//        if(nbt == null) return null;
//        String itemId = nbt.getString(KANSKY_ITEM_ID);
//        return itemId != null ? getItemConfig(itemId) : null;
//    }
    public static @Nullable ItemConfig getItemConfig(ItemStack itemStack){
        if(!KanskyUtil.isItemStack(itemStack)) return null;
        String itemId = NBT.modify(itemStack, ItemStackNBTProxy.class, ItemStackNBTProxy::getKanskyItemId);
//        return getItemConfig(NBT.readNbt(itemStack));
        return itemId != null ? getItemConfig(itemId) : null;
    }
    public static boolean is(ItemStack itemStack,String itemId){
        return itemConfigMap.get(itemId) == getItemConfig(itemStack);
    }
}
