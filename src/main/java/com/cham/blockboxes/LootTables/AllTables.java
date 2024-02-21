package com.cham.blockboxes.LootTables;


import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllTables {

    public static Inventory getTables() {
        Inventory inventory = Bukkit.createInventory(null, 54);
        for(Table table : getAllTables()) {
            inventory.addItem(table.getBoxItem());
        }
        return inventory;
    }

    public static List<Table> getAllTables() {
        List<Table> all = new ArrayList<>();
        File dataFolder = BlockBoxes.getShitBoxes().getDataFolder();
        File[] files = dataFolder.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isFile() && file.getName().endsWith(".yml")) {
                    String fileName = file.getName();
                    String tableId = fileName.replace(".yml", "");
                    Table table = Table.getTableFromId(tableId);
                    all.add(table);
                }
            }
        }
        return all;
    }

}
