package com.cavepvp.skywars.scoreboard.provider;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.scoreboard.AssembleAdapter;
import com.cavepvp.skywars.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {

    public String getTitle(Player player) {
        return Skywars.getInstance().getConfig().getString("scoreboard.title");
    }

    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();

        switch (Skywars.getInstance().getGame().getState()) {
            case STARTING:
                for (String s : Skywars.getInstance().getConfig().getStringList("scoreboard.starting")) {
                    s = s.replace("%seconds%", Integer.toString(Skywars.getInstance().getGame().getTimerByName("GameStart").getCurrentSecond()))
                            .replace("%online%", Integer.toString(Bukkit.getServer().getOnlinePlayers().size()))
                            .replace("%max%", Integer.toString(Skywars.getInstance().getGame().getMaximumPlayers()))
                            .replace("%map%", Skywars.getInstance().getGame().getActiveArena().getDisplayName())
                            .replace("%gamemode%", Skywars.getInstance().getGame().getGamemode().name());
                    toReturn.add(s);
                }
                break;
            case PLAYING:
                for (String s : Skywars.getInstance().getConfig().getStringList("scoreboard.playing")) {
                    s = s.replace("%online%", Integer.toString(Bukkit.getServer().getOnlinePlayers().size()))
                            .replace("%max%", Integer.toString(Skywars.getInstance().getGame().getMaximumPlayers()))
                            .replace("%gamemode%", Skywars.getInstance().getGame().getGamemode().name())
                            .replace("%alive%", Integer.toString(Skywars.getInstance().getGame().getAlivePlayers().size()))
                            .replace("%kills%", Integer.toString(Skywars.getInstance().getGame().getPlayerKills().get(player.getUniqueId())))
                            .replace("%map%", Skywars.getInstance().getGame().getActiveArena().getDisplayName());

                    if (Skywars.getInstance().getGame().getActiveEvent() == null) {
                        s = s.replace("%time%", "???")
                                .replace("%event%", "None");
                    } else {
                        s = s.replace("%time%", TimeUtil.parseToTime(Skywars.getInstance().getGame().getActiveEvent().getTimer().getCurrentSecond()))
                                .replace("%event%", Skywars.getInstance().getGame().getActiveEvent().getName());
                    }

                    toReturn.add(s);
                }
                break;
            case ENDING:
                for (String s : Skywars.getInstance().getConfig().getStringList("scoreboard.ending")) {
                    s = s.replace("%player%", Skywars.getInstance().getGame().getWinner().getName())
                            .replace("%time%", Skywars.getInstance().getGame().getTimerByName("ServerShutdown").getCurrentSecond() + "s");
                    toReturn.add(s);
                }
                break;
            default:
                toReturn.add("&4&lAN ERROR OCCURED!");
                break;
        }

        return toReturn;
    }
}
