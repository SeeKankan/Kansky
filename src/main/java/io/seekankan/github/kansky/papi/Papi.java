package io.seekankan.github.kansky.papi;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.Message;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class Papi {
    private static boolean isPapiEnable = false;
    /**
     *
     * @return do the placeholder api plugin enable
     */
    public static boolean loadPapi() {
        Kansky instance = Kansky.getInstance();
        Logger logger = instance.getLogger();
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            logger.info(Message.DEPEND__PAPI_REGISTER_EXPANSION.getMessage());
            //register expansion
            regExpansions();
            isPapiEnable = true;
            return true;
        }else{
            logger.info(Message.DEPEND__PAPI_MISSING.getMessage());
            isPapiEnable = false;
            return false;
        }
    }

    public static boolean isPapiEnable() {
        return isPapiEnable;
    }

    private static void regExpansions() {
        new AttributeExpansion().register();
        if(Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            new MythicMobsSlayerExpansion().register();
        }
    }
}
