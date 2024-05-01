package com.cham.blockboxes.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ItemUtil {

    public static ItemStack buildItem(Material material, String name, String... lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        is.setItemMeta(meta);
        return is;
    }

}
