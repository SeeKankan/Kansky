package io.seekankan.github.kansky.papi;

import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.kanattribute.AttributeTracker;
import io.seekankan.github.kansky.mythicmobs.SlayerInstance;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MythicMobsSlayerExpansion extends PlaceholderExpansion {
    private static final String IDENTIFIER = "kanskyslayer";
    @Override
    public @NotNull String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public @NotNull String getAuthor() {
        return Kansky.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return Kansky.getInstance().getDescription().getVersion();
    }
    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().isPluginEnabled("MythicMobs");
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        SlayerInstance instance = new SlayerInstance(player);
        switch (params) {
            case "name":
                Optional<MythicMob> mythicMob = instance.getSlayerMob();
                return mythicMob.map(mob -> mob.getDisplayName().get()).orElseGet(Message.MYTHIC_MOBS__NOT_CHOOSE_SLAYER::getMessage);
            case "exp":
                return String.valueOf(instance.getSlayerExp());
            case "requireexp":
                return String.valueOf(instance.getSlayerMob()
                        .map(mob -> mob.getConfig().getInteger("KanSlayerRequireExp",0))
                        .orElse(0));
            case "level":
                return String.valueOf(instance.getLevel());
        }
        return null;
//        return String.format("%.1f", AttributeTracker.trackModifier(player).getOrDefault(params,0d));
    }
}
