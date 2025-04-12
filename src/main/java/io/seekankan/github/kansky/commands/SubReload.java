package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Config;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.inventory.GUI;
import io.seekankan.github.kansky.js.JS;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.listener.HealthListener;
import io.seekankan.github.kansky.util.KanPermission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubReload implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Kansky.getInstance().loadReloadableConfig();
        Kansky.getInstance().getLogger().info(Message.COMMAND__RELOAD_SUCCESS.getMessage());
        sender.sendMessage(Message.COMMAND__RELOAD_SUCCESS.getMessage());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission(KanPermission.KANSKY_RELOAD);
    }

}
