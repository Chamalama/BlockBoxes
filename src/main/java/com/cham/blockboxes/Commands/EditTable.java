package com.cham.blockboxes.Commands;

import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EditTable implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("table.edit")) {
            ItemStack is = p.getInventory().getItemInMainHand();
            if(Table.isTable(is)) {
                Table table = Table.tableFromItem(is);
                CreateTable.getPlayerEditingTable().put(p.getUniqueId(), table);
                p.sendMessage(ChatColor.GREEN + "Now editing " + table.getTableId());
            }
        }

        return true;
    }
}
