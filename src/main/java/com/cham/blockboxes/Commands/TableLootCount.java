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

import java.io.IOException;

public class TableLootCount implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player p = (Player) commandSender;
        if(p.hasPermission("table.loot")) {
            ItemStack is = p.getItemInHand();
            if(Table.isTable(is)) {
                if(strings.length == 1) {
                    int lootCount = Integer.parseInt(strings[0]);
                    Table table = Table.tableFromItem(is);
                    table.setLootCount(lootCount);
                    p.sendMessage(ChatColor.YELLOW + "(!) Set table loot count to: " + lootCount);
                    try {
                        BlockBoxes.getShitBoxes().getLootTablesConfig().saveLootTable(table);
                    }catch (IOException e) {
                        Bukkit.getLogger().info("[Boxes] Error occurred while setting loot count... " + table.getTableId());
                    }
                    BlockBoxes.getShitBoxes().getLootTablesConfig().loadLootTables();
                }else{
                    p.sendMessage(ChatColor.RED + "Usage: /lootcount (Number)");
                    return false;
                }
            }
        }

        return true;
    }
}
