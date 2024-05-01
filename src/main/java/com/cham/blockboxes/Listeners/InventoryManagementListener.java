package com.cham.blockboxes.Listeners;

import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.Commands.CreateTable;
import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class InventoryManagementListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (CreateTable.getPlayerEditingTable().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            Table table = CreateTable.getPlayerEditingTable().get(player.getUniqueId());;
            ItemStack is = player.getInventory().getItemInMainHand();
            if (!event.getMessage().equalsIgnoreCase("stop") && !event.getMessage().equalsIgnoreCase("beacon") && !event.getMessage().equalsIgnoreCase("spawner")) {
                if (is.getType() != Material.AIR) {
                    int chance;
                    try {
                        chance = Integer.parseInt(event.getMessage());
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "(!) Please enter a valid number");
                        return;
                    }
                    int fullNum = chance;
                    if (chance != 0) {
                        LootItem lootItem = new LootItem(is, chance);

                        int currentWeight = table.getWeight();
                        int newWeight = currentWeight + fullNum;
                        table.setWeight(newWeight);
                        table.getLootTable().add(lootItem);
                        player.sendMessage("Table Updated: " + table.getTableId());
                        player.getInventory().remove(table.getBoxItem());
                        try {
                            BlockBoxes.getShitBoxes().getLootTablesConfig().saveLootTable(table);
                        } catch (IOException exception) {
                            Bukkit.getLogger().info("[TABLES] " + exception.getMessage());
                        }
                        update(table);
                        player.getInventory().addItem(table.getBoxItem());
                        player.updateInventory();
                    }
                }
            }else if(event.getMessage().equalsIgnoreCase("beacon")) {
                updateTableItem(table, Material.BEACON);
                player.sendMessage(ChatColor.GRAY + "Changed item type to beacon");
            }else if(event.getMessage().equalsIgnoreCase("spawner")) {
                updateTableItem(table, Material.SPAWNER);
                player.sendMessage(ChatColor.GRAY + "Changed item type to spawner");
            }else if(event.getMessage().equalsIgnoreCase("stop")) {
                try {
                    BlockBoxes.getShitBoxes().getLootTablesConfig().saveLootTable(table);
                }catch (IOException exception) {
                    Bukkit.getLogger().info("[TABLES] " + exception.getMessage());
                }
                player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "STOPPED EDIT... WAIT 3 SECONDS FOR LOAD!!!");
                for(File file : BlockBoxes.getShitBoxes().getDataFolder().listFiles()) {
                    if(file.getName().replace(".yml", "").equalsIgnoreCase(table.getTableId())) {
                        Bukkit.getScheduler().runTaskLater(BlockBoxes.getShitBoxes(), () ->{
                            BlockBoxes.getShitBoxes().getLootTablesConfig().loadLootTableFromFile(file);
                            Bukkit.getLogger().info("[TABLES] Loaded: " + file.getName());
                            player.sendMessage("Loaded table: " + file.getName());
                        }, 60L);
                    }
                }
                Bukkit.getLogger().info("[TABLES] TABLE ID: " + table.getTableId());
                CreateTable.getPlayerEditingTable().remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(Table.inventories.contains(event.getInventory())) {
            event.setCancelled(true);
        }
    }

    public static void update(Table table) {
        ItemStack boxItem = table.getBoxItem();
        ItemMeta meta = boxItem.getItemMeta();

        for (LootItem lootItem : table.getLootTable()) {
            Bukkit.getLogger().info("ITEMS: "  + table.getTableId() + " " + lootItem.getIs().getItemMeta().getDisplayName() + " " + lootItem.getIs().getType() + "\n");
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            if (lore != null) {
                if (!lore.contains(ChatColor.GRAY + "Contains: ")) {
                    lore.add(0, ChatColor.GRAY + "Contains: ");
                }
                if (!lore.contains(ChatColor.RED + "")) {
                    lore.add(1, ChatColor.RED + "");
                }
                if (!lore.contains(ChatColor.GRAY + "- " + lootItem.getIs().getItemMeta().getDisplayName())) {
                    lore.add(ChatColor.GRAY + "- " + lootItem.getIs().getItemMeta().getDisplayName());
                    meta.setLore(lore);
                }
            }
        }
        boxItem.setItemMeta(meta);
    }

    private void updateTableItem(Table table, Material material) {
        ItemStack newItem = new ItemStack(material);
        newItem.setItemMeta(table.getBoxItem().getItemMeta());
        table.setBoxItem(newItem);
    }

}
