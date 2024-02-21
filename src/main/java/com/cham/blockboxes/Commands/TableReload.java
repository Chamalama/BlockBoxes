package com.cham.blockboxes.Commands;

import com.cham.blockboxes.BlockBoxes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TableReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("Table.Reload")) {
            BlockBoxes.getShitBoxes().getLootTablesConfig().loadLootTables();
            p.sendMessage("Tables reloaded...");
        }

        return true;
    }
}
