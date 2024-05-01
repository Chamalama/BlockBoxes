package com.cham.blockboxes;

import com.cham.blockboxes.Commands.*;
import com.cham.blockboxes.Config.LootTableConfig;
import com.cham.blockboxes.Listeners.InventoryManagementListener;
import com.cham.blockboxes.Listeners.PlayerOpenLootBox;
import com.cham.blockboxes.Util.Table;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

public final class BlockBoxes extends JavaPlugin {

    public static BlockBoxes shitBoxes;
    private LootTableConfig config;

    public static Hashtable<String, Table> tableName = new Hashtable<>();

    @Override
    public void onEnable() {
        shitBoxes = this;
        config = new LootTableConfig(this);
        registerCommands();
        registerListeners();
        config.loadLootTables();
    }

    @Override
    public void onDisable() {
        tableName.clear();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryManagementListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerOpenLootBox(), this);
    }
    public void registerCommands() {
        getCommand("createtable").setExecutor(new CreateTable());
        getCommand("showtables").setExecutor(new RetrieveTables());
        getCommand("givetable").setExecutor(new GetTable());
        getCommand("edittable").setExecutor(new EditTable());
        getCommand("tableremove").setExecutor(new TableRemove());
        getCommand("lootcount").setExecutor(new TableLootCount());
    }

    public static Hashtable<String, Table> getTableName() {
        return tableName;
    }

    public static Table getTable(String name) {
        return tableName.get(name);
    }

    public static BlockBoxes getShitBoxes() {
        return shitBoxes;
    }
    public LootTableConfig getLootTablesConfig() {
        return config;
    }
}
