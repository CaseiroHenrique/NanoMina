package com.plugin.commands;

import com.plugin.NanoMina;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MinaRestart implements CommandExecutor {

    private NanoMina plugin;

    public MinaRestart(NanoMina plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            // Teleportar todos os jogadores do mundo "mina" para o mundo "world"
            World minaWorld = Bukkit.getWorld("mina");
            World defaultWorld = Bukkit.getWorld("world");

            if (minaWorld != null && defaultWorld != null) {
                for (Player player : minaWorld.getPlayers()) {
                    player.teleport(defaultWorld.getSpawnLocation());
                }
            }

            // Reiniciar o plugin
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Plugin thisPlugin = Bukkit.getPluginManager().getPlugin(plugin.getName());
                if (thisPlugin != null) {
                    Bukkit.getPluginManager().disablePlugin(thisPlugin);
                    Bukkit.getPluginManager().enablePlugin(thisPlugin);
                }
            }, 20L); // Aguarda 1 segundo (20 ticks) antes de executar

            sender.sendMessage("O plugin NanoMina está sendo reiniciado...");
            return true;
    }
}
