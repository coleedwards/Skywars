package com.cavepvp.skywars.event.worldend;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.event.Event;
import com.cavepvp.skywars.event.EventListener;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.timer.TimerListener;
import com.cavepvp.skywars.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class WorldEndEvent extends Event {

    public WorldEndEvent() {
        this.setName("WorldEnd");

        Timer timer = new Timer();
        timer.setName(getName() + "Timer");
        timer.setListener(new EventListener());
        timer.setActive(false);
        timer.setCurrentSecond(300);

        this.setTimer(timer);

        this.setActive(false);
    }

    @Override
    protected void onEventStart() {
        Bukkit.broadcastMessage(ColorUtil.translate("&cThe world soon becomes infected and everyone becomes poisoned..."));

        for (UUID uuid : Skywars.getInstance().getGame().getAlivePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 1));
        }
    }
}
