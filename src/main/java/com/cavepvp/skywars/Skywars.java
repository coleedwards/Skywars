package com.cavepvp.skywars;

import com.cavepvp.skywars.arena.Arena;
import com.cavepvp.skywars.commands.ArenaCommand;
import com.cavepvp.skywars.commands.SetItemsCommand;
import com.cavepvp.skywars.commands.SetMinPlayersCommand;
import com.cavepvp.skywars.game.Game;
import com.cavepvp.skywars.listeners.*;
import com.cavepvp.skywars.scoreboard.Assemble;
import com.cavepvp.skywars.scoreboard.AssembleStyle;
import com.cavepvp.skywars.scoreboard.provider.ScoreboardProvider;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Skywars extends JavaPlugin {

    @Getter
    private static Skywars instance;

    private Game game;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        setupInstances();
        setupCommands();
        setupListeners();
        setupStuff();
    }

    @Override
    public void onDisable() {
        getGame().getActiveArena().degenerateArena();
        instance = null;
    }

    private void setupInstances() {
        for (File file : getDataFolder().listFiles()) {
            String fN = StringUtils.capitalize(file.getName().replace(".schematic", ""));

            if (file.getName().endsWith(".schematic")) {

                Arena arena = new Arena(file.getName());

                arena.setName(fN);

                Arena.getArenas().add(arena);
            }
        }

        game = new Game();
    }

    private void setupCommands() {
        getCommand("setminplayers").setExecutor(new SetMinPlayersCommand());
        getCommand("setitems").setExecutor(new SetItemsCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
    }

    private void setupListeners() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new LeaveListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new FoodListener(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new InteractListener(), this);
        pm.registerEvents(new PickupListener(), this);
    }

    private void setupStuff() {
        Assemble assemble = new Assemble(this, new ScoreboardProvider());
        assemble.setAssembleStyle(AssembleStyle.KOHI);
    }
}