package com.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.World;

public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Menu da Mina")) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;

            Player player = (Player) event.getWhoClicked();
            World minaWorld = Bukkit.getWorld("mina");

            switch (event.getSlot()) {
                case 15: // "Ir para mina"
                    Location safeLocation = findSafeLocation(minaWorld);
                    if (safeLocation != null) {
                        player.teleport(safeLocation);
                    } else {
                        player.sendMessage("Não foi possível encontrar um local seguro para teletransporte.");
                    }
                    break;
                // Implemente a lógica para os outros itens
            }
        }
    }
    private Location findSafeLocation(World world) {
        int safeY = world.getHighestBlockYAt(0, 0);
        Location loc = new Location(world, 0.5, safeY, 0.5);

        Block block = world.getBlockAt(loc);
        Block above = block.getRelative(BlockFace.UP);
        Block below = block.getRelative(BlockFace.DOWN);

        if (isSafeBlock(below.getType()) && isSafeBlock(block.getType()) && isSafeBlock(above.getType())) {
            return loc;
        }

        return null;
    }
    private boolean isSafeBlock(Material material) {
        return !material.isSolid() && !material.isBurnable() && material != Material.LAVA && material != Material.WATER;
    }
}
