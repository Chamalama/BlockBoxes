package com.cham.blockboxes.Commands;

import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.Listeners.InventoryManagementListener;
import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableRemove implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("table.remove")) {
            ItemStack is = p.getInventory().getItemInMainHand();
            if(Table.isTable(is)) {
                Table toEdit = Table.tableFromItem(is);
                if(strings.length == 1) {
                    int index = Integer.parseInt(strings[0]);
                    p.sendMessage(ChatColor.RED + "Removed " + toEdit.getLootTable().get(index).getIs().getItemMeta().getDisplayName() + " from the loot table!");
                    LootItem itemToRemove = toEdit.getLootTable().get(index);
                    p.getInventory().remove(toEdit.getBoxItem());
                    toEdit.getLootTable().remove(itemToRemove);
                    double weight = itemToRemove.getChance();
                    ItemMeta meta = toEdit.getBoxItem().getItemMeta();
                    List<String> lore = new ArrayList<>();
                    for (LootItem lootItem : toEdit.getLootTable()) {
                        if (lootItem != null) {
                            lore.add(ChatColor.GRAY + "- " + lootItem.getIs().getItemMeta().getDisplayName());
                        }
                    }
                    if (!lore.contains(ChatColor.GRAY + "Contains: ")) {
                        lore.add(0, ChatColor.GRAY + "Contains: ");
                    }
                    if (!lore.contains(ChatColor.RED + "")) {
                        lore.add(1, ChatColor.RED + "");
                    }
                    toEdit.setWeight((int) (toEdit.getWeight() - weight));
                    meta.setLore(lore);
                    toEdit.getBoxItem().setItemMeta(meta);
                    try {
                        BlockBoxes.getShitBoxes().getLootTablesConfig().saveLootTable(toEdit);
                    }catch (Exception e) {
                        Bukkit.getLogger().info("[TABLES] Remove Cause: " + e.getCause());
                    }
                    p.getInventory().addItem(toEdit.getBoxItem());
                    p.updateInventory();
                }
            }
        }
        return true;
    }
}
