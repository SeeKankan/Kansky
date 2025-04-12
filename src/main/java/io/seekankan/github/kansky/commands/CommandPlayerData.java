package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.util.KanPermission;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class CommandPlayerData implements TabExecutor {
    final Map<String,SubCommand> subCommandMap = new HashMap<String,SubCommand>(){{
        put("list",new SubListPlayerData());
    }};
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtil.onCommand(sender,command,label,args,
                subCommandMap,
                KanPermission.KANSKY_PLAYER_DATA,
                Message.COMMAND__NOT_ENOUGH_PERMISSION,
                Message.COMMAND__PLAYER_DATA__USAGE,
                Message.COMMAND__INVALID_ARGS);
//        if(!sender.hasPermission(KanPermission.KANSKY_PLAYER_DATA)){
//            sender.sendMessage(Message.COMMAND__NOT_ENOUGH_PERMISSION.getMessage());
//            return true;
//        }
//        if(args.length == 0) {
//            sender.sendMessage(Message.COMMAND__PLAYER_DATA__USAGE.getMessage());
//            return true;
//        }
//        String[] subArgs = Arrays.copyOfRange(args,1,args.length);
//        SubCommand subCommand = subCommandMap.get(args[0]);
//        if(subCommand != null) {
//            if(!subCommand.checkPermission(sender)) {
//                sender.sendMessage(Message.COMMAND__NOT_ENOUGH_PERMISSION.getMessage());
//                return true;
//            }
//            return subCommand.onCommand(sender, command, label, subArgs);
//        }
//        else sender.sendMessage(Message.COMMAND__INVALID_ARGS.getMessage() + Message.COMMAND__PLAYER_DATA__USAGE.getMessage());
//        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return KanskyUtil.filterCommand(sender,command,alias,args,subCommandMap);
    }
}
