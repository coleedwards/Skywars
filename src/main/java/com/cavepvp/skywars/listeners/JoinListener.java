package com.cavepvp.skywars.listeners;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.arena.spawn.SpawnPoint;
import com.cavepvp.skywars.game.GameState;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.util.ColorUtil;
import com.cavepvp.skywars.util.FreezeUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setHelmet(null);
        e.getPlayer().getInventory().setChestplate(null);
        e.getPlayer().getInventory().setLeggings(null);
        e.getPlayer().getInventory().setBoots(null);
        e.getPlayer().setGameMode(GameMode.SURVIVAL);

        Skywars.getInstance().getGame().getPlayerKills().put(e.getPlayer().getUniqueId(), 0);

        if (Skywars.getInstance().getGame().getState() == GameState.STARTING) {

            if (Skywars.getInstance().getGame().getAlivePlayers().size() >= Skywars.getInstance().getGame().getMaximumPlayers()) {
                Skywars.getInstance().getGame().setSpectator(e.getPlayer());

                e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 200, 0));
                return;
            }

            if (Bukkit.getServer().getOnlinePlayers().size() >= Skywars.getInstance().getGame().getMinimumPlayers() && !Skywars.getInstance().getGame().getTimerByName("GameStart").isActive()) {
                Skywars.getInstance().getGame().getTimerByName("GameStart").start();
            }

            if (Bukkit.getServer().getOnlinePlayers().size() == Skywars.getInstance().getGame().getMaximumPlayers() && !Skywars.getInstance().getGame().getTimerByName("GameStart").isActive()) {
                Timer t = Skywars.getInstance().getGame().getTimerByName("GameStart");

                t.setCurrentSecond(10);
                t.start();
            }

            Skywars.getInstance().getGame().getAlivePlayers().add(e.getPlayer().getUniqueId());
            e.getPlayer().setHealth(20.0);
            FreezeUtil.freeze(e.getPlayer());

            Bukkit.broadcastMessage(ColorUtil.translate("&4" + e.getPlayer().getName() + " &chas joined! &4" + Bukkit.getServer().getOnlinePlayers().size() + "/" + Skywars.getInstance().getGame().getMaximumPlayers()));

            for (SpawnPoint spawnPoint : Skywars.getInstance().getGame().getActiveArena().getSpawnPoints()) {
                if (!spawnPoint.isTaken() && spawnPoint.getUuid() == null) {
                    e.getPlayer().teleport(spawnPoint.getLocation());
                    spawnPoint.setUuid(e.getPlayer().getUniqueId());
                    return;
                }
            }
        } else {
            Skywars.getInstance().getGame().setSpectator(e.getPlayer());

            e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 200, 0));
        }
    }
}