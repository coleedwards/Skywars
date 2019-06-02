package com.cavepvp.skywars.commands;

import com.cavepvp.skywars.Skywars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMinPlayersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("skywars.setminplayers")) {
                if (args.length != 1) {
                    p.sendMessage(ChatColor.RED + "Usage: /setminplayers <number>");
                    return true;
                }

                try {
                    int i = Integer.parseInt(args[0]);

                    Skywars.getInstance().getGame().setMinimumPlayers(i);

                    p.sendMessage(ChatColor.GREEN + "Successfully set the minimum players to " + i);
                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.RED + "Usage: /setminplayers <number>");
                    return true;
                }
            } else {
                p.sendMessage(ChatColor.RED + "No permission.");
            }
        }
        return true;
    }

}
