package com.cavepvp.skywars.event;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.timer.TimerListener;

public class EventListener implements TimerListener {

    @Override
    public void onTick(Timer timer, int i) {
        if (i == 0) {
            for (Event event : Skywars.getInstance().getGame().getEvents()) {
                if (timer.equals(event.getTimer())) {
                    event.onEventStart();
                }
            }
        }
    }

}
