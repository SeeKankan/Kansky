package io.seekankan.github.kansky.forge;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import io.seekankan.github.kansky.util.PlayerDatum;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ForgeDataUtil{
    private static final String NAMESPACE_FORGE = "Forge";
    private static final String START_TIME = "StartTime";
    private static final String ITEM = "Item";
    private static final String RAW_MATERIAL = "RawMaterial";

    public static ForgeMeta getForgeMeta(Player player,int forgeSlot) {
        ReadWriteNBT nbt = getForgeNBT(player).getOrCreateCompound(String.valueOf(forgeSlot));
        long startTime = nbt.getLong(START_TIME);
        String item = nbt.getString(ITEM);
        ItemStack[] rawMaterial = nbt.getItemStackArray(RAW_MATERIAL);
        return new ForgeMeta(startTime,item,rawMaterial);
    }
    public static void setForgeMeta(Player player,int forgeSlot,ForgeMeta meta){
        ReadWriteNBT nbt = getForgeNBT(player).getOrCreateCompound(String.valueOf(forgeSlot));
        nbt.setLong(START_TIME, meta.getStartTime());
        nbt.setString(ITEM, meta.getItem());
        nbt.setItemStackArray(RAW_MATERIAL,meta.getRawMaterial());
    }
    public static long getEndTime(ForgeMeta meta) {
        long startTime = meta.getStartTime();
        long requireTime = meta.getForgeItem().getTime();
        long endTime = startTime + requireTime;
        return endTime - System.currentTimeMillis();
    }
    private static ReadWriteNBT getForgeNBT(Player player){
        return PlayerDatum.pluginPlayerData.getPlayerData(player).getOrCreateCompound(NAMESPACE_FORGE);
    }
}
