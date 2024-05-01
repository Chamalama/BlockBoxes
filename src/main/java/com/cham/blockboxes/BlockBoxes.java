package com.cham.blockboxes;

import com.cham.blockboxes.Commands.*;
import com.cham.blockboxes.Config.LootTableConfig;
import com.cham.blockboxes.Listeners.InventoryManagementListener;
import com.cham.blockboxes.Listeners.PlayerOpenLootBox;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockBoxes extends JavaPlugin {

    public static BlockBoxes shitBoxes;
    private LootTableConfig config;
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

    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryManagementListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerOpenLootBox(), this);
    }
    public void registerCommands() {
        getCommand("createtable").setExecutor(new CreateTable());
        getCommand("gettables").setExecutor(new RetrieveTables());
        getCommand("givetable").setExecutor(new GetTable());
        getCommand("edittable").setExecutor(new EditTable());
        getCommand("tableremove").setExecutor(new TableRemove());
        getCommand("tablereload").setExecutor(new TableReload());
    }

    public static BlockBoxes getShitBoxes() {
        return shitBoxes;
    }
    public LootTableConfig getLootTablesConfig() {
        return config;
    }
}
