package com.cham.blockboxes.LootTables;

import com.cham.blockboxes.Util.Table;
import org.bukkit.inventory.ItemStack;

public class OneLineTable extends Table {
    public OneLineTable(String tableId, ItemStack boxItem, int weight, int lootCount) {
        super(tableId, boxItem, weight, lootCount);
    }
}
