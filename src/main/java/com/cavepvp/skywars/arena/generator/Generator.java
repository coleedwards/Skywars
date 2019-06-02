package com.cavepvp.skywars.arena.generator;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Credit to EmptyWorldGenerator
 * I suck at world stuff lul
 */

public class Generator extends ChunkGenerator {

    @Getter private static List<BedrockCoords> bedrockCoords = new ArrayList<>();

    public byte[][] generateBlockSections(final World world, final Random random, final int chunkX, final int chunkZ, final ChunkGenerator.BiomeGrid biomeGrid) {
        final byte[][] result = new byte[world.getMaxHeight() / 16][];
        final Iterator<BedrockCoords> it = getBedrockCoords().iterator();
        while (it.hasNext()) {
            final BedrockCoords block = it.next();
            if (block.x >= chunkX * 16 && block.x < (chunkX + 1) * 16 && block.z >= chunkZ * 16 && block.z < (chunkZ + 1) * 16) {
                int x = block.x % 16;
                if (x < 0) {
                    x += 16;
                }
                int z = block.z % 16;
                if (z < 0) {
                    z += 16;
                }
                this.setBlock(result, x, block.y, z, (byte)7);
                it.remove();
            }
        }
        return result;
    }

    private void setBlock(final byte[][] result, final int x, final int y, final int z, final byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][(y & 0xF) << 8 | z << 4 | x] = blkid;
    }
}
