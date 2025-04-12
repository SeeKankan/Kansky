package io.seekankan.github.kansky.mythicmobs.commands;

import de.tr7zw.changeme.nbtapi.NBT;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.inventory.GUI;
import io.seekankan.github.kansky.inventory.SlayerGUIHolder;
import io.seekankan.github.kansky.mythicmobs.SlayerInstance;
import io.seekankan.github.kansky.util.KanPermission;
import io.seekankan.github.kansky.util.KanskyUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class CommandSlayer implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission(KanPermission.KANSKY_SLAYER)) {
                player.sendMessage(Message.COMMAND__NOT_ENOUGH_PERMISSION.getMessage());
                return true;
            }
            if(args.length == 0) player.openInventory(GUI.createInventory("slayer",new SlayerGUIHolder(),player));
            else {
                String slayerName = args[0];
                MythicMob mm = MythicMobs.inst().getMobManager().getMythicMob(slayerName);
                if(mm == null) {
                    sender.sendMessage(Message.MYTHIC_MOBS__INVALID_SLAYER.getMessage());
                    return true;
                } else {
                    SlayerInstance slayer = new SlayerInstance(player);
                    slayer.setSlayerMob(mm);
                    slayer.clearSlayerExp();
                }
            }
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
