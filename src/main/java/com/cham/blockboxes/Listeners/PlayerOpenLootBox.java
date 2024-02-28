package com.cham.blockboxes.Listeners;

import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerOpenLootBox implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.HAND) {
                Player player = event.getPlayer();
                ItemStack is = player.getEquipment().getItem(EquipmentSlot.HAND);
                if (Table.isTable(is)) {
                    String displayName = is.getItemMeta().getDisplayName();
                    int lastIndex = displayName.lastIndexOf("7");
                    String result = displayName.substring(0, lastIndex - 1);
                    Table table = Table.getTableFromId(result);
                    if (table != null) {
                        if(player.getInventory().firstEmpty() == -1) {
                            player.sendMessage(ChatColor.RED + "(!) Must have an open inventory slot!");
                            return;
                        }
                        if (table.getBoxItem().getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName())) {
                            if (is.getAmount() > 1) {
                                is.setAmount(is.getAmount() - 1);
                            } else {
                                player.getInventory().setItem(EquipmentSlot.HAND, null);
                            }
                            LootItem item = LootItem.getRandomItem(table);
                            Bukkit.getLogger().info("[TABLES] Player: " + player.getName() + " obtained " + item.getIs().getItemMeta().getDisplayName() + " from " +
                                    ChatColor.stripColor(table.getBoxItem().getItemMeta().getDisplayName()).replace("(Right Click)", ""));
                            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.75F, 1.2F);
                            player.getInventory().addItem(new ItemStack(item.getIs()));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        ItemStack is = p.getInventory().getItemInMainHand();
        if(Table.isTable(is)) {
            p.sendMessage(ChatColor.RED + "Cannot place loot table chests.");
            event.setCancelled(true);
        }
    }

}
