package com.plugin;

import com.plugin.commands.MinaCommand;
import com.plugin.world.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class NanoMina extends JavaPlugin {

    @Override
    public void onEnable() {
        registerWorldGenerator();

        this.getCommand("mina").setExecutor(new MinaCommand());

        getLogger().info("NanoMina iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NanoMina desativado!");
    }


    private void registerWorldGenerator() {
        String worldName = "mina";

        World existingWorld = Bukkit.getWorld(worldName);
        if (existingWorld != null) {
            if (!existingWorld.getPlayers().isEmpty()) {
                World safeWorld = Bukkit.getWorlds().get(0);
                for (Player player : existingWorld.getPlayers()) {
                    player.teleport(safeWorld.getSpawnLocation());
                }
            }

            Bukkit.unloadWorld(existingWorld, true);

            File worldFolder = existingWorld.getWorldFolder();
            deleteWorldFolder(worldFolder);
        }

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new WorldGenerator());
        Bukkit.createWorld(worldCreator);
    }

    private void deleteWorldFolder(File path) {
        if (path.exists()) {
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                if (files != null) {
                    for (File file : files) {
                        deleteWorldFolder(file);
                    }
                }
            }
            path.delete();
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }

}

