package io.seekankan.github.kansky.util;

import io.seekankan.github.kansky.Config;
import io.seekankan.github.kansky.Kansky;

import java.io.File;

public class PlayerDatum {
    public static PlayerData pluginPlayerData = new PlayerData(new File(Kansky.getInstance().getDataFolder(),"playerdata"));

    public static void preloadData() {
        if(Config.autoSave != 0){
            pluginPlayerData.startAutoSave(Config.autoSave);
        }
    }

    public static void closeAll(){
        pluginPlayerData.saveData();
        pluginPlayerData.endAutoSave();
    }
}
