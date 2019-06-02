package com.cavepvp.skywars.commands;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.gamemode.Gamemode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SetItemsCommand implements CommandExecutor {

    Random r = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("cavepvp.admin")) {

                if (args.length != 1) {
                    p.sendMessage(ChatColor.RED + "Usage: /setitems <gamemode>");
                    return true;
                }

                Gamemode gamemode = Gamemode.valueOf(args[0]);

                Inventory inv = p.getInventory();

                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack is = inv.getItem(i);

                    Skywars.getInstance().getConfig().set("items." + gamemode.name() + "."  + r.nextInt(1000), is);
                }

                Skywars.getInstance().saveConfig();
                Skywars.getInstance().reloadConfig();

                p.sendMessage(ChatColor.GREEN + "Completed.");

            } else {
                p.sendMessage(ChatColor.RED + "No permission.");
            }
        }
        return false;
    }

}
