package com.cavepvp.skywars.death;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.death.listeners.DamageListener;
import com.cavepvp.skywars.death.listeners.DeathListener;
import lombok.Data;
import org.bukkit.Bukkit;

@Data
public class Death {

    public Death() {
        Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), Skywars.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new DamageListener(), Skywars.getInstance());
    }
}
