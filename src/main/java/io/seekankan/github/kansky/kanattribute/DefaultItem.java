package io.seekankan.github.kansky.kanattribute;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.event.KanItemCreateEvent;
import io.seekankan.github.kansky.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DefaultItem implements ConfigurationSerializable {
    private static YamlConfiguration defYAML;
    private static final Map<String,DefaultItem> defMap = new HashMap<>(); //all default item will register at there

    public final String displayName;
    public final Material material;
    public final String itemId;
    public final ReadableNBT nbt;

    @Contract("_ -> new")
    @SuppressWarnings("unused")
    public static @NotNull DefaultItem deserialize(@NotNull Map<String,Object> map) {
        String displayName = (String) map.get("display_name");
        String strMaterial = (String) map.get("material");
        Material material = Material.valueOf(strMaterial);
        String itemId = (String) map.get("item_id");
        String strNBT = (String) map.getOrDefault("nbt","{}");
        ReadableNBT nbt = NBT.parseNBT(strNBT);
        return new DefaultItem(displayName,material,itemId,nbt);
    }
    protected DefaultItem(String displayName, Material material, String itemId, ReadableNBT nbt) {
        this.displayName = displayName;
        this.material = material;
        this.itemId = itemId;
        this.nbt = nbt;
    }
    public ItemStack create(){
        ItemStack itemStack = new ItemCreator(material).name(displayName).create();
        NBT.modify(itemStack,nbt -> {
            nbt.setString(ItemConfig.KANSKY_ITEM_ID,itemId);
            nbt.mergeCompound(nbt);
        });
        KanItemCreateEvent callEvent = new KanItemCreateEvent(itemStack,this);
        Bukkit.getPluginManager().callEvent(callEvent);
        return itemStack;
    }
    public static ItemStack createItem(String defId){
        return defMap.get(defId).create();
    }
    public static boolean isValidId(String defId){
        return defMap.containsKey(defId);
    }

    public static void loadDefaultItem(){
        defMap.clear(); //clear old default item
        ConfigurationSerialization.registerClass(DefaultItem.class);
        Kansky instance = Kansky.getInstance();
        File file = new File(instance.getDataFolder(),"default_item.yml");
        if(!file.exists()) {
            instance.getLogger().info("Create default_item.yml");
            instance.saveResource("default_item.yml",true);
        }
        defYAML = YamlConfiguration.loadConfiguration(file);
        Set<String> keys = defYAML.getKeys(false);
        keys.forEach(key -> defMap.put(key, (DefaultItem) defYAML.get(key)));
    }
    public static Map<String,DefaultItem> getDefaultItemMap(){
        return defMap;
    }

    @Override
    public Map<String, Object> serialize() {
        throw new RuntimeException("You shouldn't write default item into yaml!This isn't support,sorry.");
    }
}
