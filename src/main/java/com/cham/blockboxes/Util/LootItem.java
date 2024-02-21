package com.cham.blockboxes.Util;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LootItem {

    private ItemStack is;
    private double chance;
    private int amount;
    public LootItem(ItemStack is, int chance) {
        this.is = is;
        this.chance = chance;
    }
    public LootItem(ItemStack is, int amount, double chance) {
        this.is = is;
        this.amount = amount;
        this.chance = chance;
    }

    public ItemStack getIs() {
        return is;
    }

    public void setIs(ItemStack is) {
        this.is = is;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static LootItem getRandomItem(Table table) {
        int totalWeight = table.getWeight();
        Random random = new Random();
        int randomNum = random.nextInt(totalWeight) + 1;
        int cumulativeWeight = 0;
        for(LootItem item : table.getLootTable()) {
            cumulativeWeight += (int)item.getChance();
            if(randomNum <= cumulativeWeight) {
                return item;
            }
        }
        return null;
    }

}
