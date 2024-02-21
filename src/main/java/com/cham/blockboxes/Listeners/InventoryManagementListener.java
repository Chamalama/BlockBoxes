package com.cham.blockboxes.Listeners;

import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.Commands.CreateTable;
import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class InventoryManagementListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (CreateTable.getPlayerEditingTable().containsKey(player.getUniqueId())) {
            if(event.getMessage().equalsIgnoreCase("stop")) {
                BlockBoxes.getShitBoxes().getLootTablesConfig().loadLootTables();
                player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "LOOT TABLES RELOADED");
                CreateTable.getPlayerEditingTable().remove(player.getUniqueId());
                event.setCancelled(true);
            }
            event.setCancelled(true);
            Table table = CreateTable.getPlayerEditingTable().get(player.getUniqueId());;
            if (!event.getMessage().equalsIgnoreCase("stop")) {
                ItemStack is = player.getInventory().getItemInMainHand();
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
                        update(table);
                        player.sendMessage("Table Updated: " + table.getTableId());
                        player.getInventory().remove(table.getBoxItem());
                        player.getInventory().addItem(table.getBoxItem());
                        player.updateInventory();
                        try {
                            BlockBoxes.getShitBoxes().getLootTablesConfig().saveLootTable(table);
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        }
    }

    public static void update(Table table) {
        ItemStack boxItem = table.getBoxItem();
        ItemMeta meta = boxItem.getItemMeta();

        for (LootItem lootItem : table.getLootTable()) {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            if(!lore.contains(ChatColor.GRAY + "Contains: ")) {
                lore.add(0, ChatColor.GRAY + "Contains: ");
            }
            if(!lore.contains(ChatColor.RED + "")) {
                lore.add(1, ChatColor.RED + "");
            }
            if (!lore.contains(ChatColor.GRAY + "- " + lootItem.getIs().getItemMeta().getDisplayName())) {
                lore.add(ChatColor.GRAY + "- " + lootItem.getIs().getItemMeta().getDisplayName());
                meta.setLore(lore);
            }
        }
        boxItem.setItemMeta(meta);
    }
}
