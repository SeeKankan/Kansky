package io.seekankan.github.kansky.kanattribute;

import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rarity implements ConfigurationSerializable {
    private static YamlConfiguration rarityYAML;
    private static final HashMap<Integer,Rarity> rarities = new HashMap<>();

    private final int id;
    private final String displayName;
    private Rarity(int id,String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Contract("_ -> new")
    public static @NotNull Rarity deserialize(@NotNull Map<String,Object> map) {
        int id = (Integer) map.get("id");
        String displayName = map.get("display_name").toString();
        return new Rarity(id,displayName);
    }
    @Override
    public Map<String, Object> serialize() {
        return new HashMap<String, Object>(){{
            put("id",id);
            put("display_name",displayName);
        }};
//        throw new RuntimeException("You shouldn't write rarity into yaml!This isn't support,sorry.");
    }
    public static void loadItemConfig() {
        rarities.clear();
        ConfigurationSerialization.registerClass(Rarity.class);
//        Kansky instance = Kansky.getInstance();
//        File file = new File(instance.getDataFolder(),"rarity.yml");
//        if(!file.exists()) {
//            instance.getLogger().info("Create rarity.yml");
//            instance.saveResource("rarity.yml",true);
//        }
        rarityYAML = KanskyUtil.getConfig("rarity.yml"); //YamlConfiguration.loadConfiguration(file);
        Set<String> keys = rarityYAML.getKeys(false);
        keys.forEach(key -> rarities.put(Integer.valueOf(key), (Rarity) rarityYAML.get(key)));
    }
    public static Rarity getRarity(int id) {
        return rarities.get(id);
    }

    public int getId() {
        return id;
    }

    public String getRarityName() {
        return displayName;
    }
}
