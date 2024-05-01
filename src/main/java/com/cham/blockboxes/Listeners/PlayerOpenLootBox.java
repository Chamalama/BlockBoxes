package com.cham.blockboxes.Listeners;

import com.cham.blockboxes.Util.ItemUtil;
import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerOpenLootBox implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOpen(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getHand() == EquipmentSlot.HAND) {
                Player player = event.getPlayer();
                ItemStack is = player.getEquipment().getItem(EquipmentSlot.HAND);
                if (Table.isTable(is)) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);
                    }
                    Table table = Table.tableFromItem(is);
                    if (table != null) {
                        if (player.getInventory().firstEmpty() == -1) {
                            player.sendMessage(ChatColor.RED + "(!) Must have an open inventory slot!");
                            return;
                        }
                        if (table.getBoxItem().getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName())) {
                            if (is.getAmount() > 1) {
                                is.setAmount(is.getAmount() - 1);
                            } else {
                                player.getInventory().setItem(EquipmentSlot.HAND, null);
                            }
                            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.75F, 1.2F);
                            LootItem[] moreItems;
                            if (table.getLootCount() > 1) {
                                moreItems = new LootItem[table.getLootCount()];
                                for (int i = 0; i < table.getLootCount(); i++) {
                                    moreItems[i] = LootItem.getRandomItem(table);
                                }
                                Bukkit.getLogger().info("[TABLES] " + player.getName() + " RECEIVED LOOT FROM " + table.getTableId());
                                for(LootItem items : moreItems) {
                                    player.getInventory().addItem(items.getIs());
                                    Bukkit.getLogger().info("[TABLES] " + (items.getIs().getItemMeta().hasDisplayName() ? items.getIs().getItemMeta().getDisplayName() : ItemUtil.toProperCase(items.getIs().getType().name())));
                                }
                            } else {
                                LootItem item = LootItem.getRandomItem(table);
                                if (item != null) {
                                    Bukkit.getLogger().info("[TABLES] Player: " + player.getName() + " obtained " + item.getIs().getItemMeta().getDisplayName() + " from " +
                                            ChatColor.stripColor(table.getBoxItem().getItemMeta().getDisplayName()).replace("(Right Click)", ""));
                                    player.getInventory().addItem(item.getIs());
                                } else {
                                    try {
                                        player.getInventory().addItem(new ItemStack(item.getIs().clone()));
                                    } catch (Exception e) {
                                        Bukkit.getLogger().info("[TABLES] Item is null??? " + table.getTableId() + " might want to check this one. Probably a weight problem.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack is = p.getInventory().getItemInMainHand();
            if (Table.isTable(is)) {
                p.sendMessage(ChatColor.RED + "Cannot place loot table chests.");
                event.setCancelled(true);
            }
        }
    }
}
