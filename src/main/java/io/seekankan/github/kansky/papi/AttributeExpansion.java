package io.seekankan.github.kansky.papi;

import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.kanattribute.AttributeTracker;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AttributeExpansion extends PlaceholderExpansion {
    private static final String IDENTIFIER = "kanskyattribute";
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
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        return String.format("%.1f", AttributeTracker.trackModifier(player).getOrDefault(params,0d));
    }
}
