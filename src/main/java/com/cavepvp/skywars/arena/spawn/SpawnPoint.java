package com.cavepvp.skywars.arena.spawn;

import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class SpawnPoint {

    private Location location;
    private boolean taken;
    private UUID uuid;

}
