package com.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MinaRestart implements CommandExecutor {
    private final JavaPlugin plugin;

    public MinaRestart(JavaPlugin plugin) {
        this.plugin = plugin;
        // Este comando deve ser registrado no método onEnable do seu JavaPlugin principal.
        plugin.getCommand("mina").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && "restart".equalsIgnoreCase(args[0])) {

                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                    sender.sendMessage("§aPlugin reiniciado e atualizações carregadas!");
                }, 1L);
            return true;
        }
        sender.sendMessage("§cUso incorreto. Tente /mina restart.");
        return true;
    }
}
