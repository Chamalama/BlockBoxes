package com.cham.blockboxes.LootTables;

import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class SmallTable extends Table {
    public SmallTable() {
        super("", new ItemStack(Material.BEACON), 0, 1);
        this.inventory = Bukkit.createInventory(null, InventoryType.DISPENSER);
    }
}
