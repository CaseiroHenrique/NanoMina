package com.plugin;

import org.bukkit.WorldCreator;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Random;

public final class NanoMina extends JavaPlugin {

    @Override
    public void onEnable() {
        WorldCreator wc = new WorldCreator("mina");
        wc.generator(new CustomChunkGenerator());
        World world = wc.createWorld();
        getLogger().info("Mundo 'mina' criado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NanoMina desativado!");
    }

    public static class CustomChunkGenerator extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            ChunkData chunk = createChunkData(world);

            // Exemplo: Geração de um terreno básico sem blocos de minério
            for (int X = 0; X < 16; X++) {
                for (int Z = 0; Z < 16; Z++) {
                    for (int Y = 0; Y < world.getMaxHeight(); Y++) {
                        // Aqui você define a lógica de quais blocos adicionar ou omitir
                    }
                }
            }

            return chunk;
        }
    }
}
