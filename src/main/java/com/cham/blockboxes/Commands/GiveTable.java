package com.cham.blockboxes.Commands;

import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GiveTable implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("give.table")) {
            if (strings.length >= 2) {
                String playerName = strings[0];

                String tableName = String.join(" ", Arrays.copyOfRange(strings, 1, strings.length));

                if (playerName.isEmpty()) {
                    p.sendMessage(ChatColor.RED + "(!) Please enter a valid player name!");
                    return false;
                }
                Player to_give = Bukkit.getPlayer(playerName);
                Table tableToGet = null;
                for(String tableNames : BlockBoxes.getTableName().keySet()) {
                    if(tableName.equalsIgnoreCase(tableNames)) {
                        tableToGet = BlockBoxes.getTable(tableNames);
                        break;
                    }
                }
                if (tableToGet == null) {
                    p.sendMessage(ChatColor.RED + "(!) Invalid table name!");
                    return false;
                }
                ItemStack is = tableToGet.getBoxItem();
                to_give.getInventory().addItem(is);
            } else {
                p.sendMessage(ChatColor.RED + "(!) Usage: /givetable <playerName> <tableName>");
                return false;
            }
        }
        return true;
    }
}
