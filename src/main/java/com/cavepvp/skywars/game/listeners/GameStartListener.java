package com.cavepvp.skywars.game.listeners;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.game.GameState;
import com.cavepvp.skywars.game.win.WinRunnable;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.timer.TimerListener;
import com.cavepvp.skywars.util.ColorUtil;
import com.cavepvp.skywars.util.FreezeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameStartListener implements TimerListener {

    public void onTick(Timer timer, int i) {
        if (timer.getName().equalsIgnoreCase("GameStart")) {
            if (i <= 5 && i != 0) {
                Bukkit.broadcastMessage(ColorUtil.translate("&cThe game is starting in " + i));

                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
                }
            }

            if (i == 0) {
                Bukkit.broadcastMessage(ColorUtil.translate("&cThe game has started!"));

                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0f, 1.0f);
                }

                new WinRunnable().runTaskTimerAsynchronously(Skywars.getInstance(), 0L, 20L);

                Bukkit.broadcastMessage(ColorUtil.translate("&4Map: &c" + Skywars.getInstance().getGame().getActiveArena().getDisplayName()));

                Skywars.getInstance().getGame().setState(GameState.PLAYING);

                Bukkit.getServer().getOnlinePlayers().forEach(p -> FreezeUtil.unfreezePlayer(p));

                Skywars.getInstance().getGame().getChest().fillChests();
                Skywars.getInstance().getGame().activateEvent(Skywars.getInstance().getGame().getEventByName("Refill"));
            }

        }
    }

}
