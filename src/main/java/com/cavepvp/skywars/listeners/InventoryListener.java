package com.cavepvp.skywars.listeners;

import com.cavepvp.skywars.Skywars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
