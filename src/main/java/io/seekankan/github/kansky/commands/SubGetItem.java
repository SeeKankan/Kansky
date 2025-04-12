package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.kanattribute.DefaultItem;
import io.seekankan.github.kansky.util.KanPermission;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SubGetItem implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Message.COMMAND__INVALID_SENDER.getMessage());
            return true;
        }
        Player player = (Player) sender;
        ArrayList<ItemStack> items = new ArrayList<>(args.length);
        for(String defId : args){
            if(!DefaultItem.isValidId(defId)) continue;
            items.add(DefaultItem.createItem(defId));
        }
        KanskyUtil.addItemOverflow(player,items.toArray(new ItemStack[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String latest = null;
        List<String> list = new ArrayList<>(DefaultItem.getDefaultItemMap().keySet());
        //你的代码，一般根据args的长度、玩家的权限去查找可能会补全的单词，添加进list即可
        if (args.length != 0) {
            latest = args[args.length - 1];
        }
        KanskyUtil.filter(list, latest);
        return list;
    }

    @Override
    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission(KanPermission.KANSKY_GET_ITEM);
    }
}
