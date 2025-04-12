package io.seekankan.github.kansky.util;

import org.bukkit.permissions.Permission;

public interface KanPermission {
    Permission KANSKY_KANSKY = new Permission("kansky.user.kansky");
    Permission KANSKY_STATE = new Permission("kansky.user.state");
    Permission KANSKY_FORGE = new Permission("kansky.user.forge");
    Permission KANSKY_SLAYER = new Permission("kansky.user.slayer");

    Permission KANSKY_RELOAD = new Permission("kansky.admin.reload");
    Permission KANSKY_CLEAR_ATTRIBUTE = new Permission("kansky.admin.clearattribute");
    Permission KANSKY_GET_ITEM = new Permission("kansky.admin.getitem");
    Permission KANSKY_PLAYER_DATA = new Permission("kansky.admin.playerdata");
    Permission KANSKY_ITEM_NBT = new Permission("kansky.admin.itemnbt");
}
