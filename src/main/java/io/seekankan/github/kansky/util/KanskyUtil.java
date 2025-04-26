package io.seekankan.github.kansky.util;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import io.seekankan.github.kansky.Config;
import io.seekankan.github.kansky.Kansky;
import io.seekankan.github.kansky.Message;
import io.seekankan.github.kansky.commands.SubCommand;
import io.seekankan.github.kansky.forge.ForgeItem;
import io.seekankan.github.kansky.kanattribute.AttributeTracker;
import io.seekankan.github.kansky.kanattribute.DamageModifier;
import io.seekankan.github.kansky.kanattribute.ItemConfig;
import io.seekankan.github.kansky.kanattribute.Slot;
import io.seekankan.github.kanutil.util.Maps;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KanskyUtil {
    private KanskyUtil() {}
    public static final UUID CLEAR_ARMOR_UUID = new UUID(0xfadf0cc2,0x0fc96de84);
    public static final UUID CLEAR_ATTACK_SPEED = new UUID(0xfa926ea9,0x2057afec);
    public static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static ReadableNBT[] getNBTs(ItemStack[] items) {
        ReadableNBT[] nbts = new ReadableNBT[items.length];
        getNBTs(nbts,items);
        return nbts;
    }
    public static void getNBTs(ReadableNBT[] nbts,ItemStack[] items) {
        int index = 0;
        for(ItemStack item : items) {
            if(item == null || item.getAmount() == 0 || item.getType() == Material.AIR) continue;
            nbts[index] = NBT.readNbt(item);
            index++;
        }
    }
    public static <K> void mergeMapByPlus(Map<K,Double> writeableMap,Map<K,Double>[] maps){
        if(writeableMap == null || maps == null) return;
        for(Map<K,Double> map : maps){
            if(map == null) continue;
            map.forEach((k, num) -> {
                if(k == null || num == null) return;
                writeableMap.put(k, writeableMap.getOrDefault(k,0D) + num);
            });
        }
    }
    public static <K> void mergeMapByPlus(Map<K,Double> writeableMap,List<Map<K,Double>> maps){
        if(writeableMap == null || maps == null) return;
        for(Map<K,Double> map : maps){
            if(map == null) continue;
            map.forEach((k, num) -> {
                if(k == null || num == null) return;
                writeableMap.put(k, writeableMap.getOrDefault(k,0D) + num);
            });
        }
    }
    public static double getDamage(Map<String,Double> entityModifier,Map<String,Double> damagerModifier) {
        double damage = 0;
        for(DamageModifier modifier : AttributeTracker.damagerModifier) {
            damage = modifier.handleDamage(damage,damagerModifier);
        }
        for(DamageModifier modifier : AttributeTracker.entityModifier) {
            damage = modifier.handleDamage(damage,entityModifier);
        }
        return damage;
    }
    public static String getEntityName(Entity entity) {
        return chooseNotNull(new String[]{
                entity.getCustomName(),
                entity.getName(),
                entity.getType().name()
        },"" /* This shouldn't happen! */);
    }
    public static <T> T getNotNull(@Nullable T t,@NotNull T def){
        return t != null ? t : def;
    }
    public static <T> T chooseNotNull(T[] list,T def) {
        for(T t : list) {
            if(t != null) return t;
        }
        return def;
    }
    public static boolean isItemStack(@Nullable ItemStack itemStack){
        return itemStack != null && itemStack.getType() != Material.AIR && itemStack.getAmount() != 0;
    }
    @Deprecated
    public static ItemStack[] getItemsFromEquipment(@NotNull EntityEquipment eq){
        return new ItemStack[] {
                eq.getItemInMainHand(),
                eq.getItemInOffHand(),
                eq.getHelmet(),
                eq.getChestplate(),
                eq.getLeggings(),
                eq.getBoots()
        };
    }
    public static Map<Slot, List<ItemStack>> getSlotFromEquipment(EntityEquipment eq){
        return Maps.asEnumMap(
                Slot.class,
                Slot.MAINHAND,Collections.singletonList(eq.getItemInMainHand()),
                Slot.OFFHAND,Collections.singletonList(eq.getItemInOffHand()),
                Slot.HEAD,Collections.singletonList(eq.getHelmet()),
                Slot.CHESTPLATE,Collections.singletonList(eq.getChestplate()),
                Slot.LEGGINGS,Collections.singletonList(eq.getLeggings()),
                Slot.BOOTS,Collections.singletonList(eq.getBoots())
        );
    }
    public static void addItemOverflow(Player player,ItemStack... items){
        PlayerInventory pInv = player.getInventory();
        HashMap<Integer,ItemStack> overflowItem = pInv.addItem(items);
        overflowItem.forEach((index,itemStack) -> {
            Item item = (Item) player.getWorld().spawnEntity(player.getLocation(), EntityType.DROPPED_ITEM);
//            NBT.modify(item,nbt -> {
//                nbt.setString("Owner",player.getName());
//                nbt.setString("Thrower",player.getName());
//            });
            NBT.modify(item, ItemNBTProxy.class, proxy -> {
                proxy.setOwner(player.getName());
                proxy.setThrower(player.getName());
            });
            item.setItemStack(itemStack);
            item.setPickupDelay(0);
        });
    }
    public static void filter(List<String> list, String latest){
        if (list.isEmpty() || latest == null)
            return;
        String ll = latest.toLowerCase();
        list.removeIf(k -> !k.toLowerCase().startsWith(ll));
    }
    public static List<String> filterCommand(CommandSender sender, Command command, String alias, String[] args,Map<String,SubCommand> subCommandMap) {
        if(args.length == 1){
            String latest;
            List<String> list = new ArrayList<>(subCommandMap.keySet());
            latest = args[args.length - 1];
            KanskyUtil.filter(list, latest);
            return list;
        }
        SubCommand subCommand = subCommandMap.get(args[0]);
        String[] subArgs = Arrays.copyOfRange(args,1,args.length);
        return subCommand == null ? null : subCommand.onTabComplete(sender, command, alias, subArgs);
    }
    public static int roundUp(int x,int y) {
        return x / y + (x % y != 0 ? 1 : 0);
    }
    public static boolean filterInventory(InventoryClickEvent event, Class<? extends InventoryHolder> holderClazz){
        Inventory inventory = event.getClickedInventory();
        InventoryHolder holder = event.getInventory().getHolder();
        if(inventory == null) return true;
        if(!(holderClazz.isInstance(holder))) return true;
        event.setCancelled(true);
        return inventory instanceof PlayerInventory;
    }
    public static String timeToHMS(long displayTime) {
        StringBuilder timeSb = new StringBuilder();

        long hour = displayTime/(60*60*1000);
        long minute = (displayTime - hour*60*60*1000)/(60*1000);
        long second = (displayTime - hour*60*60*1000 - minute*60*1000)/1000;
//
//        long seconds = displayTime / 60;
//        long listSeconds = displayTime % 60;
//        long listMinutes = seconds % 60;
//        long listHours = seconds / 60;
        return timeSb.append(hour)
                .append(Message.TIME__HOUR.getMessage())
                .append(' ')
                .append(minute)
                .append(Message.TIME__MINUTE.getMessage())
                .append(' ')
                .append(second)
                .append(Message.TIME__SECOND.getMessage())
                .toString();
    }
    public static ItemStack[] deleteKanItem(Player player, ForgeItem forgeItem) {
        return deleteKanItem(player,forgeItem.getRecipe());
    }
    public static ItemStack[] deleteKanItem(HumanEntity player, Map<String,Integer> recipe) {
        Inventory inventory = player.getInventory();
        HashMap<String,Integer> knowRecipe = new HashMap<>();
        HashMap<String,Integer> recipeClone = new HashMap<>(recipe);
        HashMap<ItemStack,String> items = new HashMap<>();
        for (ItemStack itemStack : inventory) {
            if(!KanskyUtil.isItemStack(itemStack)) continue;
            String id = ItemConfig.getKanskyItemId(itemStack);
            if(id == null || "".equals(id)) continue;
            knowRecipe.put(id, knowRecipe.getOrDefault(id,0) + itemStack.getAmount());

            items.put(itemStack,id);
//            int amount = itemStack.getAmount();
//            int decreaseAmount = Math.min(amount, recipeClone.get(id));
//            itemStack.setAmount(amount - decreaseAmount);
//            recipeClone.put(id,recipeClone.get(id) - decreaseAmount);
        }
        for(String kanItemId : recipe.keySet()) {
            int count = recipe.get(kanItemId);
            Integer knowCount = knowRecipe.get(kanItemId);
            if(knowCount == null || knowCount < count) return null;
        }
        ArrayList<ItemStack> cloneItems = new ArrayList<>(items.keySet().size());
        items.forEach(((itemStack, id) -> {
            cloneItems.add(itemStack.clone());
            int amount = itemStack.getAmount();
            Integer getRecipe = recipeClone.get(id);
            if(getRecipe == null) return;
            int decreaseAmount = Math.min(amount, getRecipe);
            itemStack.setAmount(amount - decreaseAmount);
            recipeClone.put(id,recipeClone.get(id) - decreaseAmount);
        }));
        return cloneItems.toArray(new ItemStack[0]);
    }
    public static void clearArmor(Attributable attributable) {
        AttributeModifier attributeModifier = new AttributeModifier(CLEAR_ARMOR_UUID,"clear",-1, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        attributable.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(attributeModifier);
        attributable.getAttribute(Attribute.GENERIC_ARMOR).addModifier(attributeModifier);
    }
    public static void clearAttackSpeed(Attributable attributable){
        AttributeModifier attributeModifier = new AttributeModifier(CLEAR_ATTACK_SPEED,"clear",1024, AttributeModifier.Operation.ADD_NUMBER);
        if(attributable.getAttribute(Attribute.GENERIC_ATTACK_SPEED) == null) return;
        attributable.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(attributeModifier);
        if(Config.infinityAttackSpeed) attributable.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(attributeModifier);
    }
    public static byte minMax(byte num,byte min,byte max) {
        if (num < min) {
            return min;
        } else {
            return num > max ? max : num;
        }
    }
    public static short minMax(short num,short min,short max) {
        if (num < min) {
            return min;
        } else {
            return num > max ? max : num;
        }
    }
    public static int minMax(int num,int min,int max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }
    public static long minMax(long num,long min,long max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }
    public static float minMax(float num,float min,float max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }
    public static double minMax(double num,double min,double max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }
    public static long toGameTick(long time) {
        return time / 50;
    }
    public static YamlConfiguration getConfig(String ymlName) {
        Kansky instance = Kansky.getInstance();
        File file = new File(instance.getDataFolder(),ymlName);
        if(!file.exists()) {
            instance.getLogger().info("Create " + ymlName);
            instance.saveResource(ymlName,true);
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
