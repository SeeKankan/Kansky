package io.seekankan.github.kansky.forge;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ForgeFunctionRegistry {
    private static final HashMap<String,ForgeFunction> forgeFunctions = new HashMap<>();

    public static void registerFunction(String name,ForgeFunction function){
        forgeFunctions.put(name,function);
    }

    public static void invokeFunction(String funcName, ItemStack itemStack, List<ItemStack> items, List<String> args) {
        System.out.println(forgeFunctions);
        System.out.println(funcName);
        System.out.println(forgeFunctions.get(funcName));
        forgeFunctions.get(funcName).handleItemStack(itemStack,items,args);
    }
}
