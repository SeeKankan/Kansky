package io.seekankan.github.kansky.mythicmobs;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.util.PlayerDatum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SlayerInstance {
    private static final String NAMESPACE_SLAYER = "Slayer";
    private static final String SLAYER_NAME = "SlayerName";
    private static final String SLAYER_EXP = "Exp";
    private final UUID playerUUID;

    public SlayerInstance(OfflinePlayer player) {
        this(player.getUniqueId());
    }
    public SlayerInstance(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
    public String getSlayerName() {
        ReadWriteNBT nbt = PlayerDatum.pluginPlayerData.getPlayerData(playerUUID).getOrCreateCompound(NAMESPACE_SLAYER);
        return nbt.getString(SLAYER_NAME);
    }
    public void setSlayerName(@Nullable String name) {
        ReadWriteNBT nbt = PlayerDatum.pluginPlayerData.getPlayerData(playerUUID).getOrCreateCompound(NAMESPACE_SLAYER);
        if(name == null) {
            nbt.removeKey(SLAYER_NAME);
            return;
        }
        nbt.setString(SLAYER_NAME,name);
    }
    public Optional<MythicMob> getSlayerMob() {
        String name = getSlayerName();
        if(name == null) return Optional.empty();
        return Optional.ofNullable(MythicMobs.inst().getMobManager().getMythicMob(name));
    }
    public void setSlayerMob(MythicMob mob) {
        setSlayerName(mob == null ? null : mob.getInternalName());
    }
    public int getSlayerExp() {
        ReadWriteNBT nbt = PlayerDatum.pluginPlayerData.getPlayerData(playerUUID).getOrCreateCompound(NAMESPACE_SLAYER);
        if(nbt.hasTag(SLAYER_EXP)) return nbt.getInteger(SLAYER_EXP);
        else {
            nbt.setInteger(SLAYER_EXP,0);
            return 0;
        }
    }
    public void setSlayerExp(int exp) {
        ReadWriteNBT nbt = PlayerDatum.pluginPlayerData.getPlayerData(playerUUID).getOrCreateCompound(NAMESPACE_SLAYER);
        nbt.setInteger(SLAYER_EXP,exp);
    }
    public void clearSlayerExp() {
        setSlayerExp(0);
    }
    public void addSlayerExp(Location dieMobLocation,int exp) {
        addSlayerExpNoCheck(exp);
        if(checkSpawn()) spawnMob(dieMobLocation);
    }
    public void addSlayerExpNoCheck(int exp) {
        setSlayerExp(getSlayerExp() + exp);
    }

    /**
     * KanSlayerLevel: level
     * @return
     */
    public int getLevel() {
        return getSlayerMob().flatMap(mythicMob ->
                Optional.of(mythicMob.getConfig().getInteger("KanSlayerLevel",1)))
                .orElse(1);
    }
    public ActiveMob spawnMob(Location location) {
        ActiveMob mob = summonMob(location,getLevel());
        if(mob != null) clearSlayerExp();
        return mob;
    }
    public ActiveMob summonMob(Location location, int level) {
        MythicMob mythicMob = getSlayerMob().orElseThrow(() -> new NullPointerException("Cannot spawn null mob!"));
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            Kansky.getInstance().getLogger().warning("Spawn slayer failed. Cause: the player isn't online.");
            return null;
        }
        return mythicMob.spawn(BukkitAdapter.adapt(location),level);
    }

    /**
     * KanSlayerRequireExp: exp
     * @return
     */
    public boolean checkSpawn() {
        Optional<MythicMob> mobOptional =  getSlayerMob();
        Optional<Boolean> result = mobOptional.flatMap(mythicMob -> {
            int require = mythicMob.getConfig().getInteger("KanSlayerRequireExp");
            return Optional.of(getSlayerExp() >= require);
        });
        return result.orElse(false);
    }
}
