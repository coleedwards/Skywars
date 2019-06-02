package com.cavepvp.skywars.timer;

import com.cavepvp.skywars.Skywars;
import lombok.Data;
import org.bukkit.scheduler.BukkitRunnable;

@Data
public class Timer extends BukkitRunnable {

    private String name;
    private boolean active;
    private int currentSecond;

    private TimerListener listener;

    @Override
    public void run() {
        if (active) {
            currentSecond--;
            if (listener != null) listener.onTick(this, currentSecond);
            if (currentSecond == 0) {
                cancel();
            }
        } else {
            cancel();
        }
    }

    public void start() {
        this.active = true;
        this.runTaskTimerAsynchronously(Skywars.getInstance(), 0L, 20L);
    }
}
