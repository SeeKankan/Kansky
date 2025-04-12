package io.seekankan.github.kansky.commands;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.util.KanPermission;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class CommandItemNBT implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission(KanPermission.KANSKY_ITEM_NBT)) {
                player.sendMessage(Message.COMMAND__NOT_ENOUGH_PERMISSION.getMessage());
                return true;
            }
            ItemStack itemStack = player.getEquipment().getItemInMainHand();
            if(!KanskyUtil.isItemStack(itemStack)) {
                player.sendMessage("{}");
                return true;
            }
           NBT.get(itemStack,getNbt -> {
                player.sendMessage(getNbt.toString());
            });
        } else {
            sender.sendMessage(Message.COMMAND__INVALID_SENDER.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
