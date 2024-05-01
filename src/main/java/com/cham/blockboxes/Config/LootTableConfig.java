package com.cham.blockboxes.Config;

import com.cham.blockboxes.BlockBoxes;
import com.cham.blockboxes.LootTables.DefaultTable;
import com.cham.blockboxes.Util.ItemUtil;
import com.cham.blockboxes.Util.LootItem;
import com.cham.blockboxes.Util.Table;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class LootTableConfig {

    private File dataFolder;
    private BlockBoxes shitBoxes;
    public LootTableConfig(BlockBoxes shitBoxes) {
        this.shitBoxes = shitBoxes;
        this.dataFolder = shitBoxes.getDataFolder();
    }

    public void saveLootTable(Table table) throws IOException {
        File file = new File(dataFolder, table.getTableId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<LootItem> lootTable = table.getLootTable();
        ItemStack mainItem = table.getBoxItem();
        config.set("mainItem", mainItem);
        int tableWeight = table.getWeight();
        config.set("weight", tableWeight);
        int lootCount = table.getLootCount();
        config.set("count", lootCount);
        for (int i = 0; i < lootTable.size(); i++) {
            LootItem item = lootTable.get(i);
            String key = "lootItem" + i;
            config.set(key + ".lootItem", item.getIs());
            config.set(key + ".chanceToDrop", item.getChance());
        }
        Table.getLootTables().add(table);
        BlockBoxes.getTableName().put(formatTableName(table.getTableId().toLowerCase()), table);
        config.save(file);
    }

    public void loadLootTables() {
        if (dataFolder != null) {
            File[] files = dataFolder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file != null && file.isFile() && file.getName().endsWith(".yml")) {
                        loadLootTableFromFile(file);
                    }
                }
            }
        }
    }

    public void loadLootTableFromFile(File file) {
        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String tableName = file.getName().replace(".yml", "");

            Table table = new DefaultTable();

            Set<String> keys = config.getKeys(false);
            int weight = config.getInt("weight");
            int lootCount = config.getInt("count");
            table.setLootCount(lootCount);
            table.setWeight(weight);
            ItemStack mainItem = config.getItemStack("mainItem");
            table.setTableId(tableName);

            for (String key : keys) {
                if(key.startsWith("lootItem")) {
                    ItemStack itemStack = config.getItemStack(key + ".lootItem");
                    int chanceToDrop = config.getInt(key + ".chanceToDrop");
                    LootItem lootItem = new LootItem(itemStack, chanceToDrop);
                    table.getLootTable().add(lootItem);
                    table.setBoxItem(mainItem);
                }
            }

            Table.getLootTables().add(table);
            BlockBoxes.getTableName().put(formatTableName(tableName.toLowerCase()), table);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String  formatTableName(String name) {
        for(int i = 0; i < name.length(); i++) {
            name = ItemUtil.checkFormatting(name);
        }
        return name;
    }


}
