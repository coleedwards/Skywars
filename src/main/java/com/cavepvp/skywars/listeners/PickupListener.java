package com.cavepvp.skywars.listeners;

import com.cavepvp.skywars.Skywars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
