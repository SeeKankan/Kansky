package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.forge.Forge;
import io.seekankan.github.kansky.util.KanPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SubForge implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Message.COMMAND__INVALID_SENDER.getMessage());
            return true;
        }
        Player player = (Player) sender;
        Forge.openMainForgeGUI(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission(KanPermission.KANSKY_FORGE);
    }
}
