package com.cham.blockboxes.Commands;

import com.cham.blockboxes.LootTables.SmallTable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MobTable implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("MOB.EDIT")) {
            p.openInventory(new SmallTable().getInventory());
        }

        return true;
    }
}
