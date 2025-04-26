package io.seekankan.github.kansky;

import de.tr7zw.changeme.nbtapi.NBT;
import io.seekankan.github.kansky.api.KanskyApi;
import io.seekankan.github.kansky.commands.CommandItemNBT;
import io.seekankan.github.kansky.commands.CommandKansky;
import io.seekankan.github.kansky.commands.CommandPlayerData;
import io.seekankan.github.kansky.forge.Forge;
import io.seekankan.github.kansky.inventory.GUI;
import io.seekankan.github.kansky.js.JS;
import io.seekankan.github.kansky.kanattribute.*;
import io.seekankan.github.kansky.listener.*;
import io.seekankan.github.kansky.loreupdaters.LoreUpdaters;
import io.seekankan.github.kansky.mythicmobs.MythicMobsHelper;
import io.seekankan.github.kansky.papi.Papi;
import io.seekankan.github.kansky.util.PlayerDatum;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Kansky extends JavaPlugin {
    private static Kansky instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        instance = this;
        loadReloadableConfig();

        if (!NBT.preloadApi()) {
            getLogger().warning(Message.NBT__INIT_FAIL.getMessage());
            getPluginLoader().disablePlugin(this);
            return;
        }
//        try {
//            Class.forName("io.seekankan.github.kansky.util.PlayerDatum");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        PlayerDatum.preloadData();

        regListeners();
        AttributeTracker.attributeTrackers.addAll(Arrays.asList(
                new DefaultAttributeTracker(),
                new ItemAttributeTracker()
                ));
        regModifiers();
        MythicMobsHelper.initMythicMobs();
        regCommands();
        Papi.loadPapi();
        Bukkit.getServicesManager().register(KanskyApi.class,new KanskyApi(),this, ServicePriority.Normal);
        long end = System.currentTimeMillis();
        getLogger().info("Loading plugin takes " + (end - start) + " millisecond");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        PlayerDatum.closeAll();
        Bukkit.getServicesManager().unregisterAll(this);
        instance = null;
    }

    public void loadReloadableConfig() {
        Bukkit.getScheduler().cancelTasks(this);
        Message.loadMessage();
        saveDefaultConfig();
        Config.loadConfig();
        AttributeFormat.loadAttributeFormat();
        Rarity.loadItemConfig();
        ItemConfig.loadItemConfig();
        DefaultItem.loadDefaultItem();
//        AttributeTracker.modifierCache.clear();
//        if(Config.doCalcAsync) AttributeTracker.startCalcAsync();
        new HealthListener().runTaskTimer(this,0,10L);
        GUI.loadGui();
        Forge.loadForge();
        Forge.getForgeConfig().getRecipeGUIConfig().initShowRecipe();
        LoreUpdaters.loadLoreUpdater();
        new UpdateForgeMainGUIListener().runTaskTimer(this,0,Config.refreshCooldown);
        if(Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) MythicMobsHelper.loadMythicMobs();
        JS.loadJS();
    }

    public static Kansky getInstance() {
        return instance;
    }

    private void regListeners() {
        regListener(new Listener[]{
                new EntityDamageListener(),
                new EntityDamageBlocker(),
                new HealthListener(),
                new AttackCooldownChangeListener(),
                new StateGUIListener(),
                new ExeCommandGUIListener(),
                new DropProtectListener(),
                new ForgeMainGUIListener(),
                new ForgeRecipeGUIListener(),
                new UpdateForgeMainGUIListener()
        });
    }
    private void regListener(Listener[] listeners) {
        PluginManager manager = getServer().getPluginManager();
        for(Listener listener : listeners) {
            manager.registerEvents(listener,this);
        }
    }
    private void regModifiers() {
       AttributeTracker.damagerModifier.addAll(Arrays.asList(
                Damage.getInstance(),
                Strength.getInstance(),
                Crit.getInstance()));
       AttributeTracker.entityModifier.add(Defense.getInstance());
    }
    private void regCommands() {
        CommandKansky commandKansky = new CommandKansky();
        CommandPlayerData commandPlayerData = new CommandPlayerData();
        CommandItemNBT commandItemNBT = new CommandItemNBT();
        getCommand("kansky").setExecutor(commandKansky);
        getCommand("kansky").setTabCompleter(commandKansky);
        getCommand("playerdata").setExecutor(commandPlayerData);
        getCommand("playerdata").setTabCompleter(commandPlayerData);
        getCommand("itemnbt").setExecutor(commandItemNBT);
        getCommand("itemnbt").setTabCompleter(commandItemNBT);
    }
}
