package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.util.KanPermission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.Map;

public class CommandUtil {
    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args,
                             Map<String,SubCommand> subCommandMap,
                             Permission invokePermission,
                             Message notEnoughPermission,
                             Message usage,
                             Message invalidArgs) {
        if(!sender.hasPermission(invokePermission)){
            sender.sendMessage(notEnoughPermission.getMessage());
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(usage.getMessage());
            return true;
        }
        String[] subArgs = Arrays.copyOfRange(args,1,args.length);
        SubCommand subCommand = subCommandMap.get(args[0]);
        if(subCommand != null) {
            if(!subCommand.checkPermission(sender)) {
                sender.sendMessage(notEnoughPermission.getMessage());
                return true;
            }
            return subCommand.onCommand(sender, command, label, subArgs);
        }
        else sender.sendMessage(invalidArgs.getMessage() + usage.getMessage());
        return true;
    }
}
