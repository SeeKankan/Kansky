package io.seekankan.github.kansky.util;

import de.tr7zw.changeme.nbtapi.handler.NBTHandlers;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.wrapper.NBTProxy;
import io.seekankan.github.kansky.forge.ForgeItem;
import io.seekankan.github.kansky.forge.ForgeRecipeGUIConfig;
import org.bukkit.inventory.ItemStack;

public interface ItemStackNBTProxy extends NBTProxy {
    @Override
    default void init() {
        registerHandler(ItemStack.class, NBTHandlers.ITEM_STACK);
        registerHandler(ReadableNBT.class, NBTHandlers.STORE_READABLE_TAG);
        registerHandler(ReadWriteNBT.class, NBTHandlers.STORE_READWRITE_TAG);
    }

    ItemStack getItem();
    void setItem(ItemStack item);

//    ReadWriteNBT getBlockStateTag();
//    void setBlockStateTag(ReadableNBT blockState);

    //Runs: return nbt.getString("kanskyItemId");
    String getKanskyItemId();
    //Runs: nbt.setString("kanskyItemId",id);
    void setKanskyItemId(String id);

    //Runs: return nbt.hasTag(markForgeSlot);
    boolean hasMarkForgeSlot();
    //Runs: return nbt.getInteger(markForgeSlot);
    int getMarkForgeSlot();
    //Runs: nbt.setInteger(markForgeSlot,slot);
    void setMarkForgeSlot(int slot);

    //Runs: return nbt.getEnum(ACTION_KEY,ForgeRecipeGUIConfig.ClickAction .class);
    ForgeRecipeGUIConfig.ClickAction getForgeRecipeAction();
    //Runs: nbt.setEnum(ACTION_KEY,action);
    void setForgeRecipeAction(ForgeRecipeGUIConfig.ClickAction action);
    //Runs: return nbt.getString(FORGE_KEY);
    String getForgeRecipeItem();
    //Runs: nbt.setString(FORGE_KEY,item);
    void setForgeRecipeItem(String item);
    default void setForgeRecipeItem(ForgeItem forgeItem) {
        setForgeRecipeItem(forgeItem.getName());
    }

}
