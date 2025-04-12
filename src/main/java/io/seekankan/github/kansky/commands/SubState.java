package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.inventory.GUI;
import io.seekankan.github.kansky.inventory.StateInventoryHolder;
import io.seekankan.github.kansky.util.KanPermission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;

public class SubState implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Message.COMMAND__INVALID_SENDER.getMessage());
            return true;
        }
        Player player = (Player) sender;
        Inventory inventory = GUI.createInventory("state",new StateInventoryHolder(player),player);
        player.openInventory(inventory);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission(KanPermission.KANSKY_STATE);
    }
}
