package io.seekankan.github.kansky;

import io.seekankan.github.kansky.papi.Papi;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public enum Message {
    NBT__INIT_FAIL,
    DEPEND__PAPI_REGISTER_EXPANSION,
    DEPEND__PAPI_MISSING,
    COMMAND__NOT_ENOUGH_PERMISSION,
    COMMAND__RELOAD_SUCCESS,
    COMMAND__KANSKY__USAGE,
    COMMAND__KANSKY__USAGE_RAW,
    COMMAND__PLAYER_DATA__USAGE,
    COMMAND__INVALID_ARGS,
    COMMAND__INVALID_SENDER,
    MUST_DOUBLE_DROP,
    JS__LOAD_SUCCESS,
    FORGE__FINISH,
    FORGE__NOT_ENOUGH_MATERIALS,
    TIME__HOUR,
    TIME__MINUTE,
    TIME__SECOND,
    MYTHIC_MOBS__NOT_CHOOSE_SLAYER,
    MYTHIC_MOBS__INVALID_SLAYER;

    private final String key;
    Message() {
        key = name().replace("__",".");
    }
    public String getKey() {
        return key;
    }

    private static YamlConfiguration messages;
    public static void loadMessage() {
        Kansky instance = Kansky.getInstance();
        File file = new File(instance.getDataFolder(),"message.yml");
        if(!file.exists()) {
            instance.getLogger().info("Create message.yml");
            instance.saveResource("message.yml",true);
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }
    public String getMessage() {
        return messages.getString(this.key);
    }
    public String format(Player player) {
        if(Papi.isPapiEnable()) {
            return PlaceholderAPI.setPlaceholders(player,getMessage());
        } else {
            return getMessage();
        }
    }
}
