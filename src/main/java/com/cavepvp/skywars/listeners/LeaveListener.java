package com.cavepvp.skywars.listeners;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.arena.spawn.SpawnPoint;
import com.cavepvp.skywars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if (Skywars.getInstance().getGame().getState() == GameState.STARTING) {
            if (Skywars.getInstance().getGame().getTimerByName("GameStart").isActive() && Bukkit.getServer().getOnlinePlayers().size() < Skywars.getInstance().getGame().getMinimumPlayers()) {
                Skywars.getInstance().getGame().getTimerByName("GameStart").setActive(false);
            }
        }

        for (SpawnPoint spawnPoint : Skywars.getInstance().getGame().getActiveArena().getSpawnPoints()) {
            if (spawnPoint.getUuid() != null && spawnPoint.getUuid().equals(e.getPlayer().getUniqueId())) {
                spawnPoint.setTaken(false);
                spawnPoint.setUuid(null);
            }
        }

        if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getPlayer().getUniqueId())) {
            Skywars.getInstance().getGame().getSpectatingPlayers().remove(e.getPlayer().getUniqueId());
        }
    }
}
