package com.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MinaCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public MinaCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("mina").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openMenu(player);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        InventoryView view = event.getView();
        Inventory open = event.getClickedInventory();
        if (open == null) return;

        if (open.getHolder() instanceof Player && open.getSize() == 27 && view.getTitle().equals("Menu da Mina")) {
            event.setCancelled(true);
        }
    }


    private void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 27, "Menu da Mina");

        ItemStack rank = new ItemStack(Material.DIAMOND);
        ItemStack picareta = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack irParaMina = new ItemStack(Material.DIRT); // Usando terra como exemplo
        ItemMeta irParaMinaMeta = irParaMina.getItemMeta();
        irParaMinaMeta.setDisplayName(ChatColor.GREEN + "Ir para a mina");
        List<String> irParaMinaLore = new ArrayList<>();
        irParaMinaLore.add(ChatColor.GRAY + "Clique para ser teletransportado");
        irParaMinaLore.add(ChatColor.GRAY + "para a mina.");
        irParaMinaMeta.setLore(irParaMinaLore);
        irParaMina.setItemMeta(irParaMinaMeta);

        // Adicionar itens no inventário
        menu.setItem(11, rank);
        menu.setItem(13, picareta);
        menu.setItem(15, irParaMina);

        player.openInventory(menu);
    }
}
