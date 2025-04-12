package io.seekankan.github.kansky.util;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.NBTFileHandle;
import io.seekankan.github.kansky.Kansky;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerData {
    private final File directory;
    private final Plugin plugin;
    private final HashMap<UUID, NBTFileHandle> data = new HashMap<>();
    private BukkitTask task;

    public PlayerData(File directory){
        this(directory, Kansky.getInstance());
    }
    public PlayerData(File directory, Plugin plugin){
        this.directory = directory;
        this.plugin = plugin;
    }
    public void saveData(){
        saveData(plugin.getLogger());
    }
    public void saveData(Logger logger){
        if(!directory.exists() && !directory.isDirectory()) {
            directory.mkdirs();
        }
        for(UUID uuid : data.keySet()){
            try {
                data.get(uuid).save();
            } catch (IOException e) {
                logger.severe("Cannot save player '" + uuid.toString() + "' data!");
                e.printStackTrace();
            }
        }
    }
    public void startAutoSave(long delay){
        this.task = Bukkit.getScheduler().runTaskTimer(plugin,this::saveData,delay,1);
    }
    public void endAutoSave(){
        this.task.cancel();
    }
    public NBTFileHandle getPlayerData(OfflinePlayer player){
        return getPlayerData(player.getUniqueId());
    }
    public NBTFileHandle getPlayerData(UUID uuid){
        NBTFileHandle nbt = data.get(uuid);
        if(nbt != null) return nbt;
        else{
            try {
                nbt = NBT.getFileHandle(new File(directory,uuid.toString() + ".dat"));
                data.put(uuid,nbt);
                return nbt;
            } catch (IOException e) {
                return null;
            }
        }
    }


}
