package com.cham.blockboxes.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TableHelp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        if(player.hasPermission("table.help")) {
            player.sendMessage("Use /ct (name) replace (name) with your table name to create the table.");
            player.sendMessage("Once you have the table in your inventory you can update it with items");
            player.sendMessage("While holding an item you want to add into the loot table, just type in a number >1");
            player.sendMessage("This will add the item to the loot table with a weight, lower means rarer if included with other items");
            player.sendMessage("Once done with the table type 'stop' and then use /tablereload to populate the tables");
        }

        return true;
    }
}
