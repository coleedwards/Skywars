package com.cavepvp.skywars.event.refill;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.event.Event;
import com.cavepvp.skywars.event.EventListener;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.util.ColorUtil;
import org.bukkit.Bukkit;

public class RefillEvent extends Event {

    public RefillEvent() {
        this.setName("Refill");

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
        Bukkit.broadcastMessage(ColorUtil.translate("&cRefilling all chests!"));
        Skywars.getInstance().getGame().getChest().fillChests();

        Skywars.getInstance().getGame().setActiveEvent(Skywars.getInstance().getGame().getEventByName("WorldEnd"));
        Skywars.getInstance().getGame().activateEvent(Skywars.getInstance().getGame().getEventByName("WorldEnd"));
    }
}
