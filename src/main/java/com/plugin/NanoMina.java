package com.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class NanoMina extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("NanoMina iniciado com sucesso!");

    }

    @Override
    public void onDisable() {
        getLogger().info("NanoMina desativado!");
    }

}
