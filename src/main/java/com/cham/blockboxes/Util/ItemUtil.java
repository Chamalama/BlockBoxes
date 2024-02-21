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

    public static ItemStack buildMoneyGemNormal(long amount) {
        return buildItem(Material.DIAMOND, ChatColor.AQUA + ChatColor.BOLD.toString() + "Money Gem " + ChatColor.GRAY + "[" + ChatColor.AQUA + format(amount) + ChatColor.GRAY + "]",
                ChatColor.GRAY + "(Right Click) " + ChatColor.WHITE + "To redeem this " + ChatColor.AQUA + "Gem",
                        ChatColor.WHITE + "and receive " + ChatColor.AQUA + "$" + format(amount));
    }

    public static String format(long format) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(format);
    }

    public static boolean isMoneyGem(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains(ChatColor.AQUA + ChatColor.BOLD.toString() + "Money Gem ");
    }
}
