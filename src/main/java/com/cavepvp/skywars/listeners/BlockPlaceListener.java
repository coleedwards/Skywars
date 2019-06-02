package com.cavepvp.skywars.listeners;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (Skywars.getInstance().getGame().getState() == GameState.STARTING || Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
