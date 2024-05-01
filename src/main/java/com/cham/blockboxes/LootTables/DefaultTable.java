package com.cham.blockboxes.LootTables;


import com.cham.blockboxes.Util.Table;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultTable extends Table {
    public DefaultTable() {
        super("", new ItemStack(Material.CHEST), 0);
    }
}
