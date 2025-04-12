package io.seekankan.github.kansky.commands;

import de.tr7zw.changeme.nbtapi.iface.NBTFileHandle;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.util.PlayerDatum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SubListPlayerData implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            NBTFileHandle nbt = PlayerDatum.pluginPlayerData.getPlayerData(player.getUniqueId());
            sender.sendMessage(String.valueOf(nbt));
        }else {
            sender.sendMessage(Message.COMMAND__INVALID_SENDER.getMessage());
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(CommandSender sender) { //check in /playerdata
        return true;
    }
}
