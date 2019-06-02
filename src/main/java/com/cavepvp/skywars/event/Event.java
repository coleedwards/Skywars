package com.cavepvp.skywars.event;

import com.cavepvp.skywars.timer.Timer;
import lombok.Data;

@Data
public abstract class Event {

    private Timer timer;
    private String name;
    private boolean active;

    protected abstract void onEventStart();

}
