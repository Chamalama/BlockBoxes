package com.cham.blockboxes.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")
public abstract class Table {

    protected String tableId;
    protected Inventory inventory;
    protected ItemStack boxItem;
    protected ArrayList<LootItem> lootTable = new ArrayList<>();
    public static Set<Table> lootTables = new HashSet<>();
    public static List<Inventory> inventories = new ArrayList<>();
    public static Set<Table> shopTable = new HashSet<>();
    public int weight;
    public int lootCount;

    public Table(String tableId, ItemStack boxItem, int weight) {
        this.tableId = tableId;
        this.boxItem = boxItem;
        this.weight = weight;
        this.lootCount = -1;
        this.inventory = Bukkit.createInventory(null, 54);
        inventories.add(inventory);
    }

    public int getLootCount() {
        return lootCount;
    }

    public void setLootCount(int lootCount) {
        this.lootCount = lootCount;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }

    public ArrayList<LootItem> getLootTable() {
        return lootTable;
    }

    public static Set<Table> getLootTables() {
        return lootTables;
    }

    public ItemStack getBoxItem() {
        return boxItem;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setBoxItem(ItemStack boxItem) {
        this.boxItem = boxItem;
    }

    public static Set<Table> getShopTable() {
        return shopTable;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static Table getTableFromId(String id) {
        for(Table table : Table.getLootTables()) {
            if((table.getTableId()).equalsIgnoreCase(id.replace("§", "&"))) {
                return table;
            }
        }
        return null;
    }
    public static boolean isTable(ItemStack is) {
        String displayName = "";
        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
            displayName = is.getItemMeta().getDisplayName();
        }
        return displayName.contains(ChatColor.GRAY + " (Right Click)");
    }

    public static Table tableFromItem(ItemStack is) {
        String displayName = is.getItemMeta().getDisplayName();
        int lastIndex = displayName.lastIndexOf("7");
        String result = displayName.substring(0, lastIndex - 1);
        return getTableFromId(result);
    }

}
