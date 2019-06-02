package com.cavepvp.skywars.death.listeners;

import com.cavepvp.skywars.Skywars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getDamager().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
