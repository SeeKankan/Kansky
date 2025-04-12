package io.seekankan.github.kansky.mythicmobs;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.mythicmobs.commands.CommandSlayer;
import io.seekankan.github.kansky.mythicmobs.listener.MythicMobListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class MythicMobsHelper {
    public static boolean initMythicMobs() {
        if(Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            regMMListeners();
            regCommands();
            return true;
        } else {
            Kansky.getInstance().getLogger().info("Cannot find plugin MythicMobs.Disable MythicMobsHelper.");
            return false;
        }
    }
    public static void loadMythicMobs() {

    }
    private static void regMMListeners() {
        regListener(new Listener[]{
            new MythicMobListener()
        });
    }
    private static void regListener(Listener[] listeners) {
        Kansky instance = Kansky.getInstance();
        PluginManager manager = Bukkit.getPluginManager();
        for(Listener listener : listeners) {
            manager.registerEvents(listener,instance);
        }
    }
    private static void regCommands() {
        Kansky inst = Kansky.getInstance();
        CommandSlayer commandSlayer = new CommandSlayer();
        inst.getCommand("slayer").setExecutor(commandSlayer);
        inst.getCommand("slayer").setTabCompleter(commandSlayer);
    }
}
