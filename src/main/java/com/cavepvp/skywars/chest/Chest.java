package com.cavepvp.skywars.chest;

import com.cavepvp.skywars.Skywars;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Chest {

    int size = 0;

    Random r = new Random();

    public void fillChests() {
        World world = Bukkit.getServer().getWorld("world");

        List<ItemStack> items = new ArrayList<>();

        for (String s : Skywars.getInstance().getConfig().getConfigurationSection("items").getConfigurationSection(Skywars.getInstance().getGame().getGamemode().name()).getKeys(false)) {
            ItemStack is = Skywars.getInstance().getConfig().getItemStack("items." + Skywars.getInstance().getGame().getGamemode().name() + "." + s);
            items.add(is);
        }

        if (size == 0) size = items.size();

        for (Chunk chunk : world.getLoadedChunks()) {
            for (BlockState blockState : chunk.getTileEntities()) {
                if (blockState instanceof org.bukkit.block.Chest) {
                    org.bukkit.block.Chest chest = (org.bukkit.block.Chest) blockState;
                    Inventory inventory = chest.getBlockInventory();

                    inventory.clear();

                    int amountOfItemsToAdd = r.nextInt((7 - 1) + 1) + 1;

                    for (int i = 0; i < amountOfItemsToAdd; i++) {
                        int randomSlot = r.nextInt(27);

                        inventory.setItem(randomSlot, items.get(getRandomNumberFromSet()));
                    }
                }
            }
        }
    }

    private int getRandomNumberFromSet() {
        return r.nextInt(size);
    }

}