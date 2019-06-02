package com.cavepvp.skywars.death.listeners;

import com.cavepvp.skywars.Skywars;
import com.google.common.base.Preconditions;
import net.minecraft.server.v1_7_R4.EntityLiving;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        String deathMessage = e.getDeathMessage();

        if (Skywars.getInstance().getGame().getSpectatingPlayers().contains(e.getEntity().getUniqueId())) return;

        if (deathMessage == null || deathMessage.isEmpty()) return;

        e.setDeathMessage(getDeathMessage(deathMessage, e.getEntity(), this.getKiller(e)));

        if (e.getEntity().getKiller() != null) {
            addKill(e.getEntity().getKiller());
        }

        Skywars.getInstance().getGame().setSpectator(e.getEntity());
    }

    private void addKill(Player p) {
        int currentKills = Skywars.getInstance().getGame().getPlayerKills().get(p.getUniqueId());

        Skywars.getInstance().getGame().getPlayerKills().remove(p.getUniqueId());
        Skywars.getInstance().getGame().getPlayerKills().put(p.getUniqueId(), currentKills + 1);
    }

    private String getDeathMessage(String input, Entity entity, Entity killer) {
        input = input.replaceFirst("\\[", "");
        input = replaceLast(input, "]", "");
        if(entity != null) {
            input = input.replaceFirst("(?i)" + this.getEntityName(entity), ChatColor.RED + this.getDisplayName(entity) + ChatColor.RED);
        }
        if(killer != null && (entity == null || !killer.equals(entity))) {
            input = input.replaceFirst("(?i)" + this.getEntityName(killer), ChatColor.RED + this.getDisplayName(killer) + ChatColor.RED);
        }
        return input;
    }

    private CraftEntity getKiller(PlayerDeathEvent event) {
        EntityLiving lastAttacker = ((CraftPlayer) event.getEntity()).getHandle().aX();
        return (lastAttacker == null) ? null : lastAttacker.getBukkitEntity();
    }

    private String getEntityName(Entity entity) {
        Preconditions.checkNotNull((Object) entity,  "Entity cannot be null");
        return (entity instanceof Player) ? ((Player) entity).getName() : ((CraftEntity) entity).getHandle().getName();
    }

    private String getDisplayName(Entity entity) {
        Preconditions.checkNotNull((Object) entity,  "Entity cannot be null");
        if(entity instanceof Player) {
            Player player = (Player) entity;
            return player.getName() + ChatColor.GRAY + " (" + Skywars.getInstance().getGame().getPlayerKills().get(player.getUniqueId()) + ")";
        }
        return WordUtils.capitalizeFully(entity.getType().name().replace('_', ' '));
    }

    public String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ')', replacement);
    }
}
