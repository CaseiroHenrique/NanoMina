package com.plugin;

import com.plugin.commands.MinaCommand;
import com.plugin.commands.MinaRestart;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class NanoMina extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getWorld("mina") != null) {
            Bukkit.unloadWorld("mina", false);
            deleteWorld(new File(Bukkit.getWorld("mina").getWorldFolder().getAbsolutePath()));
        }
        this.getCommand("mina").setExecutor(new MinaCommand(this));
        this.getCommand("minarestart").setExecutor(new MinaRestart(this));
        // Criar um novo mundo
        WorldCreator wc = new WorldCreator("mina");
        wc.generator(new CustomChunkGenerator());
        World world = wc.createWorld();
        getLogger().info("NanoMina iniciado com sucesso!");
        getLogger().info("Mundo 'mina' criado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NanoMina desativado!");
    }

    private void deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        path.delete();
    }

    public static class CustomChunkGenerator extends ChunkGenerator {
        private static final List<Material> ORES = Arrays.asList(
                Material.COAL_ORE,
                Material.IRON_ORE,
                Material.GOLD_ORE,
                Material.REDSTONE_ORE,
                Material.DIAMOND_ORE
        );

        @Override
        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            ChunkData chunk = createChunkData(world);

            for (int X = 0; X < 16; X++) {
                for (int Z = 0; Z < 16; Z++) {
                    for (int Y = 0; Y < world.getMaxHeight(); Y++) {
                        Material blockType = getBlockType(world, X, Y, Z, random, biome);
                        if (ORES.contains(blockType)) {
                            blockType = Material.STONE;
                        }
                        chunk.setBlock(X, Y, Z, blockType);
                    }
                }
            }

            return chunk;
        }

        private Material getBlockType(World world, int X, int Y, int Z, Random random, BiomeGrid biome) {
            Biome currentBiome = biome.getBiome(X, Z);
            if (Y <= 64) {
                return Material.STONE;
            }
            return Material.AIR;
        }
    }

}
