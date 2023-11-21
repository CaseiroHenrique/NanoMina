package com.plugin.world;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class WorldGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        // Limita o tamanho do mundo a 5.000 x 5.000 blocos (312 chunks em qualquer direção a partir do centro)
        if (Math.abs(chunkX) > 312 || Math.abs(chunkZ) > 312) {
            return createChunkData(world);
        }

        ChunkData chunk = createChunkData(world);

        generateTerrain(world, chunkX, chunkZ, chunk);

        removeOre(world, chunk); // Aqui você passa o objeto 'world' para o método 'removeOre'

        return chunk;
    }

    private void generateTerrain(World world, int chunkX, int chunkZ, ChunkData chunk) {
        Random random = new Random(world.getSeed());
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        generator.setScale(0.005); // Quanto menor o valor, mais suaves e maiores serão as colinas/montanhas

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int realX = x + chunkX * 16;
                int realZ = z + chunkZ * 16;

                double noise = generator.noise(realX, realZ, 0.5, 0.5) * 15 + 50;
                int baseHeight = noise < 0 ? 50 : (int) noise;

                for (int y = 0; y < baseHeight; y++) {
                    Material blockType = getBlockTypeForHeight(y, baseHeight);
                    chunk.setBlock(x, y, z, blockType);
                }

                if (baseHeight < 62) {
                    chunk.setBlock(x, 62, z, Material.LAVA);
                }
            }
        }
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> populators = new ArrayList<>();
        populators.add(new RuinsPopulator());
        populators.add(new FloatingIslandPopulator());
        return populators;
    }

    private Material getBlockTypeForHeight(int currentY, int baseHeight) {
        if (currentY < baseHeight / 3) {
            return Material.NETHERRACK;
        } else if (currentY < 2 * baseHeight / 3) {
            return Material.END_STONE;
        } else {
            return Material.STONE;
        }
    }
    private void removeOre(World world, ChunkData chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    Material type = chunk.getType(x, y, z);
                    if (isOre(type)) {
                        chunk.setBlock(x, y, z, Material.STONE);
                    }
                }
            }
        }
    }

    private boolean isOre(Material material) {
        switch (material) {
            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case DIAMOND_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case EMERALD_ORE:
            case NETHER_QUARTZ_ORE:
            case NETHER_GOLD_ORE:
                return true;
            default:
                return false;
        }
    }
}
