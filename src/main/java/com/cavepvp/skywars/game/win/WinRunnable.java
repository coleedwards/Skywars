package com.cavepvp.skywars.game.win;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.game.GameState;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.timer.TimerListener;
import com.cavepvp.skywars.util.ColorUtil;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WinRunnable extends BukkitRunnable implements TimerListener {

    public WinRunnable() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Skywars.getInstance(), "BungeeCord");
    }

    @Override
    public void onTick(Timer timer, int i) {
        if (timer.getName().equalsIgnoreCase("ServerShutdown") && i == 0) {
            Bukkit.getServer().getOnlinePlayers().forEach(p -> {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF("Hub-1");

                p.sendPluginMessage(Skywars.getInstance(), "BungeeCord", out.toByteArray());
            });

            Bukkit.getServer().getScheduler().runTask(Skywars.getInstance(), () -> {
                Skywars.getInstance().getGame().getActiveArena().degenerateArena();

                for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                }
            });

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
        }
    }

    @Override
    public void run() {
        if (Skywars.getInstance().getGame().getAlivePlayers().size() == 1) {

            Player winner = Bukkit.getServer().getPlayer(Skywars.getInstance().getGame().getAlivePlayers().iterator().next());

            for (int i = 0; i < 10; i++) {
                Bukkit.broadcastMessage(ColorUtil.translate("&4&l" + winner.getName() + " has won!"));
            }

            Skywars.getInstance().getGame().setWinner(winner);

            Skywars.getInstance().getGame().setState(GameState.ENDING);

            Skywars.getInstance().getGame().getTimerByName("ServerShutdown").setCurrentSecond(30);
            Skywars.getInstance().getGame().getTimerByName("ServerShutdown").start();

            cancel();
        }
    }
}
