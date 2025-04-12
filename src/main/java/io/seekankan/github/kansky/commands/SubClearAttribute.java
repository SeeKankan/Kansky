package io.seekankan.github.kansky.commands;

import io.seekankan.github.kansky.listener.HealthListener;
import io.seekankan.github.kansky.util.KanPermission;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class SubClearAttribute implements SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if(!(entity instanceof LivingEntity)) return;
            LivingEntity livingEntity = (LivingEntity) entity;
            AttributeInstance ai = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            ai.getModifiers().forEach(ai::removeModifier);
        }));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission(KanPermission.KANSKY_CLEAR_ATTRIBUTE);
    }
}
