package com.cham.blockboxes.Listeners;

import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerOpenLootBox implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            ItemStack is = player.getInventory().getItemInMainHand();
            if(Table.isTable(is)) {
                String displayName = is.getItemMeta().getDisplayName();
                int lastIndex = displayName.lastIndexOf("7");
                String result = displayName.substring(0, lastIndex - 1);
                Table table = Table.getTableFromId(result);
                if (table != null) {
                    List<LootItem> lootTable = table.getLootTable();
                    if(lootTable.isEmpty()) {
                        Bukkit.getLogger().info("[TABLES] LootTable empty " + table.getTableId());
                        return;
                    }
                    LootItem item;
                    item = LootItem.getRandomItem(table);
                    player.getInventory().addItem(item.getIs());
                    Bukkit.getLogger().info("[TABLES] Player: " + player.getName() + " obtained " + item.getIs().getItemMeta().getDisplayName() + " from " +
                            ChatColor.stripColor(table.getBoxItem().getItemMeta().getDisplayName()).replace("(Right Click)", ""));
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.75F, 1.2F);
                    if (is.getAmount() > 1) {
                        is.setAmount(is.getAmount() - 1);
                    } else {
                        player.getInventory().remove(is);
                    }
                }
            }
        }
    }
}
