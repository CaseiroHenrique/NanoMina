package com.plugin.world;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class FloatingIslandPopulator extends BlockPopulator {
    private static final Material[] ISLAND_MATERIALS = {
            Material.NETHERRACK, Material.END_STONE
    };

    @Override
    public void populate(World world, Random random, Chunk source) {
        if (random.nextFloat() < 0.05) {
            int centerX = (source.getX() << 4) + random.nextInt(16);
            int centerZ = (source.getZ() << 4) + random.nextInt(16);
            int centerY = 100 + random.nextInt(50);

            Material material = ISLAND_MATERIALS[random.nextInt(ISLAND_MATERIALS.length)];

            Block block = source.getBlock(centerX, centerY, centerZ);
            block.setType(material, false);
        }
    }
}
