package com.cavepvp.skywars.commands;

import com.cavepvp.skywars.Skywars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("cavepvp.admin")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.RED + "/arena <name> <schem name>");
                    return true;
                }

                String arenaName = args[0];
                String schemName = args[1];

                Skywars.getInstance().getConfig().set("arenas." + arenaName + ".schem", schemName);
                Skywars.getInstance().saveConfig();
                Skywars.getInstance().reloadConfig();
            } else {
                p.sendMessage(ChatColor.RED + "No permission.");
            }
        }
        return true;
    }

}
