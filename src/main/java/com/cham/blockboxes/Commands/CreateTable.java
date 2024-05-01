package com.cham.blockboxes.Commands;

import com.cham.blockboxes.LootTables.DefaultTable;
import com.cham.blockboxes.Util.Table;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class CreateTable implements CommandExecutor {

    public static Map<UUID, Table> playerEditingTable = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        String tableName = "";
        if(player.hasPermission("table.create")) {
            if(strings.length == 0) {
                return false;
            }
            if (!strings[0].isEmpty()) {
                for(String str : strings) {
                    tableName += str + " ";
                }
                tableName = tableName.trim();
                player.sendMessage("Creating table " + ChatColor.YELLOW + tableName + ChatColor.WHITE + "...");
                player.sendMessage("You may now hold any item in your hand and put in an Integer number to add to its loot pool.");
                player.sendMessage("Example: Hold a Diamond and type 10 and it will be added to the loot table with a weight of 10.");
                player.sendMessage("Type: 'Stop' no / to stop editing the table when finished.");
                DefaultTable defaultTable = new DefaultTable();
                defaultTable.setTableId(tableName);
                ItemMeta meta = defaultTable.getBoxItem().getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', tableName + ChatColor.GRAY + " (Right Click)"));
                defaultTable.getBoxItem().setItemMeta(meta);
                playerEditingTable.put(player.getUniqueId(), defaultTable);
                player.getInventory().addItem(defaultTable.getBoxItem());
            }
        }
        return true;
    }

    public static Map<UUID, Table> getPlayerEditingTable() {
        return playerEditingTable;
    }
}
