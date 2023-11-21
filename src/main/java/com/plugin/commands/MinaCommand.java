package com.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se quem enviou o comando é um jogador
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Obtenha o mundo "mina"
            World minaWorld = Bukkit.getWorld("mina");
            if (minaWorld != null) {
                // Defina a localização segura para teleportar
                // Aqui, você precisará de uma lógica para garantir que a localização seja segura
                Location safeLocation = findSafeLocation(minaWorld);
                // Teleporta o jogador para a localização segura
                player.teleport(safeLocation);
                player.sendMessage("Teleportado para o mundo da mina!");
            } else {
                player.sendMessage("O mundo da mina não foi encontrado!");
            }
        } else {
            sender.sendMessage("Somente jogadores podem usar esse comando!");
        }
        return true;
    }

    private Location findSafeLocation(World world) {
        int startX = 0;
        int startZ = 0;
        int radius = 50;

        for (int x = startX - radius; x <= startX + radius; x++) {
            for (int z = startZ - radius; z <= startZ + radius; z++) {
                int y = world.getHighestBlockYAt(x, z);

                Location location = new Location(world, x + 0.5, y, z + 0.5);

                if (isSafeLocation(location)) {
                    return location;
                }
            }
        }

        return new Location(world, startX + 0.5, world.getHighestBlockYAt(startX, startZ), startZ + 0.5);
    }

    private boolean isSafeLocation(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return false;
        }

        Block feet = world.getBlockAt(location);
        Block ground = world.getBlockAt(location.subtract(0, 1, 0));
        Block head = world.getBlockAt(location.add(0, 1, 0));

        return !feet.getType().isSolid() && !head.getType().isSolid() && isSafeGround(ground);
    }

    private boolean isSafeGround(Block ground) {
        Material groundType = ground.getType();
        return groundType.isSolid() && groundType != Material.LAVA && groundType != Material.FIRE
                && groundType != Material.CACTUS && !groundType.isBurnable();
    }

}
