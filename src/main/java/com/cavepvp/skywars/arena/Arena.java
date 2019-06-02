package com.cavepvp.skywars.arena;

import com.cavepvp.skywars.Skywars;
import com.cavepvp.skywars.arena.generator.Generator;
import com.cavepvp.skywars.arena.spawn.SpawnPoint;
import com.cavepvp.skywars.util.FileUtil;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.DataException;
import lombok.Data;
import lombok.Getter;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class Arena {

    @Getter private static List<Arena> arenas = new ArrayList<>();

    private String name;
    private String schematicName;
    private List<SpawnPoint> spawnPoints = new ArrayList<>();

    private CuboidClipboard clipboard;
    private EditSession editSession;

    public Arena(String schematicName) {
        this.schematicName = schematicName;
        File file = new File(Skywars.getInstance().getDataFolder().getAbsolutePath() + File.separator + schematicName);

        SchematicFormat format = SchematicFormat.MCEDIT;

        try {
            this.clipboard = format.load(file);
        } catch (DataException | IOException e) {
            e.printStackTrace();
        }
    }

    public void generateArena() {
        Location location = new Location(Bukkit.getWorld("world"), 0, 50, 0);

        editSession = loadSchematic(location);
    }

    public void degenerateArena() {
        File file = Bukkit.getServer().getWorld("world").getWorldFolder();
        Bukkit.getServer().unloadWorld("world", true);

        try {
            FileUtils.deleteDirectory(file);
            FileUtils.copyDirectory(new File(Skywars.getInstance().getDataFolder() + File.separator + "world"), new File("." + File.separator + "world"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSpawnPoints() {
        for (Chunk chunk : Bukkit.getWorld("world").getLoadedChunks()) {
            for (BlockState blockState : chunk.getTileEntities()) {
                if (blockState instanceof Sign) {
                    Sign sign = (Sign) blockState;

                    if (sign.getLine(0).contains("spawn")) {
                        Location location = sign.getLocation();

                        SpawnPoint spawnPoint = new SpawnPoint();

                        spawnPoint.setLocation(location);
                        spawnPoint.setTaken(false);

                        spawnPoints.add(spawnPoint);

                        sign.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

    public String getDisplayName() {
        return StringUtils.capitalize(name.replace("_", " "));
    }

    public EditSession loadSchematic(Location location) {
        EditSession editSession = new EditSession(new BukkitWorld(location.getWorld()), Integer.MAX_VALUE);

        try {
            this.clipboard.place(editSession, new Vector(location.getX(), location.getY(), location.getZ()), true);
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
            return null;
        }

        return editSession;
    }
}