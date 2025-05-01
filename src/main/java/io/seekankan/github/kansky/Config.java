package io.seekankan.github.kansky;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static FileConfiguration config;

    public static boolean doCalcAsync;

    public static int defenseDecrease;
    public static int minDefense;

    public static int minDamage;

    public static double strengthDivision;
    public static int strengthAdd;
    public static int minStrength;

    public static double critDamageDivision;
    public static int critDamageAdd;
    public static int minCritDamage;
    public static double critChanceAdd;

    public static int healthScale;
    public static boolean doListHealth;

    public static boolean doOverrideArmor;
    public static boolean ignoredArmorEnchantments;
    public static boolean infinityAttackSpeed;
    public static boolean bowMeleeAttack;
    public static boolean shield;
    public static boolean onlyPickSelfDrop;

    public static long autoSave;

    public static boolean isDebug;

    public static long refreshCooldown;

    public static boolean doLoreUpdate;
    public static void loadConfig() {
        Kansky instance = Kansky.getInstance();
        config = instance.getConfig();
        doCalcAsync = config.getBoolean("attribute.calc_async",true);

        defenseDecrease = config.getInt("attribute.defense_decrease",100);
        minDefense = config.getInt("attribute.defense_min",0);
        minDamage = config.getInt("attribute.damage_min",5);
        strengthDivision = config.getDouble("attribute.strength_division",100);
        strengthAdd = config.getInt("attribute.strength_add",1);
        minStrength = config.getInt("attribute.strength_min",0);
        critDamageDivision = config.getDouble("attribute.crit_damage_division",100);
        critDamageAdd = config.getInt("attribute.crit_damage_add",1);
        minCritDamage = config.getInt("attribute.crit_damage_min",0);
        critChanceAdd = config.getDouble("attribute.crit_chance_add",0.2d);

        healthScale = config.getInt("client.health_scale",40);
        doListHealth = config.getBoolean("client.health_list",true);

        doOverrideArmor = config.getBoolean("override.armor_clear",true);
        ignoredArmorEnchantments = config.getBoolean("override.ignored_armor_enchantments",false);
        infinityAttackSpeed = config.getBoolean("override.infinity_attack_speed",true);
        bowMeleeAttack = config.getBoolean("override.bow_melee_attack",false);
        shield = config.getBoolean("override.shield",true);
        onlyPickSelfDrop = config.getBoolean("override.only_pickup_self_drop",true);

        autoSave = config.getLong("autosave",1200);

        isDebug = config.getBoolean("debug",false);

        refreshCooldown = config.getLong("gui.refresh_cooldown",20);

        doLoreUpdate = config.getBoolean("lore_update",true);
    }
}
