package com.cavepvp.skywars.game;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.arena.Arena;
import com.cavepvp.skywars.chest.Chest;
import com.cavepvp.skywars.death.Death;
import com.cavepvp.skywars.event.Event;
import com.cavepvp.skywars.event.refill.RefillEvent;
import com.cavepvp.skywars.event.worldend.WorldEndEvent;
import com.cavepvp.skywars.game.listeners.GameStartListener;
import com.cavepvp.skywars.game.win.WinRunnable;
import com.cavepvp.skywars.gamemode.Gamemode;
import com.cavepvp.skywars.timer.Timer;
import com.cavepvp.skywars.timer.TimerListener;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.*;

@Data
public class Game {

    private Arena activeArena = getRandomArena();

    private GameState state = GameState.STARTING;
    private Gamemode gamemode = Gamemode.valueOf(Skywars.getInstance().getConfig().getString("gamemode"));
    private Set<Timer> timers = new HashSet<>();
    private Set<Event> events = new HashSet<>();

    private Set<UUID> alivePlayers = new HashSet<>();
    private Set<UUID> spectatingPlayers = new HashSet<>();

    private HashMap<UUID, Integer> playerKills = new HashMap<>();

    private Chest chest = new Chest();
    private Death death = new Death();

    private Random r = new Random();

    private int minimumPlayers = 4;
    private int maximumPlayers;

    private Event activeEvent;

    private Player winner;

    public Game() {
        addTimer("GameStart", new GameStartListener());

        getTimerByName("GameStart").setCurrentSecond(20);

        addTimer("ServerShutdown", new WinRunnable());

        addEvent(new RefillEvent());
        addEvent(new WorldEndEvent());

        activeArena.generateArena();
        activeArena.setSpawnPoints();

        maximumPlayers = activeArena.getSpawnPoints().size();
    }

    public void addEvent(Event event) {
        timers.add(event.getTimer());
        events.add(event);
    }

    public void addTimer(String name, TimerListener timerListener) {
        Timer timer = new Timer();

        timer.setName(name);
        timer.setActive(false);
        if (timerListener != null) timer.setListener(timerListener);

        timers.add(timer);

    }

    public void activateEvent(Event event) {
        this.setActiveEvent(event);

        event.getTimer().start();
    }

    public void setSpectator(Player p) {
        this.getSpectatingPlayers().add(p.getUniqueId());

        p.getInventory().clear();

        p.setGameMode(GameMode.CREATIVE);

        Bukkit.getServer().getOnlinePlayers().forEach(loop -> {
            if (!this.getSpectatingPlayers().contains(loop.getUniqueId()) && loop != p) {
                loop.hidePlayer(p);
            }

            if (this.getSpectatingPlayers().contains(loop.getUniqueId()) && loop != p) {
                loop.showPlayer(p);
            }
        });

        if (this.getAlivePlayers().contains(p.getUniqueId())) {
            this.getAlivePlayers().remove(p.getUniqueId());
        }

        p.teleport(Skywars.getInstance().getGame().getActiveArena().getSpawnPoints().get(0).getLocation());

        p.sendMessage(ChatColor.GREEN +"You are now a spectator.");
    }

    public Arena getRandomArena() {
        if (Arena.getArenas().size() == 1) {
            return Arena.getArenas().get(0);
        } else {
            return Arena.getArenas().get(r.nextInt(Arena.getArenas().size()));
        }
    }

    public Timer getTimerByName(String name) {
        for (Timer t : timers) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public Event getEventByName(String name) {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }

}
