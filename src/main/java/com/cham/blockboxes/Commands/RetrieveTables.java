package com.cham.blockboxes.Commands;

import com.cham.blockboxes.LootTables.AllTables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RetrieveTables implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        if(player.hasPermission("TABLE.RETRIEVE")) {
            player.openInventory(AllTables.getTables());
        }

        return true;
    }
}
