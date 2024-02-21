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
        if(player.hasPermission("TABLE.CREATE")) {
            if(strings.length == 0) {
                return false;
            }
            if (!strings[0].isEmpty()) {
                if(strings.length == 3) {
                    tableName = strings[0] + " " + strings[1] + " " + strings[2];
                }
                if(strings.length == 2) {
                    tableName = strings[0] + " " + strings[1];
                }
                if(strings.length == 1) {
                    tableName = strings[0];
                }
                player.sendMessage("Creating table " + ChatColor.YELLOW + tableName + ChatColor.WHITE + "...");
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
