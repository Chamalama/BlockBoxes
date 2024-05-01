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

    public static String toProperCase(String name) {
        String formatted;
        name = name.replace("_", " ");
        if(name.contains(" ")) {
            StringBuilder builder = new StringBuilder();
            String[] parts = name.split(" ");
            for(String s : parts) {
                s = s.toLowerCase();
                s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
                builder.append(s);
                builder.append(" ");
            }
            formatted = builder.toString();
        }else{
            name = name.toLowerCase();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return name;
        }
        return formatted;
    }

    public static String checkFormatting(String s) {
        if (s.contains("&")) {
            String reformatted;
            StringBuilder builder = new StringBuilder();
            if(s.contains(" ")) {
                String[] parts = s.split(" ");
                for (String part : parts) {
                    if(part.contains("&")) {
                        part = part.substring(2);
                    }
                    builder.append(part).append(" ");
                }
            }else{
                for (int i = 0; i < s.length(); i++) {
                    if (String.valueOf(s.charAt(i)).equalsIgnoreCase("&")) {
                        s = s.substring(2);
                        builder.append(s);
                    }
                }
            }
            reformatted = builder.toString().trim();
            return reformatted;
        }else{
            return s;
        }
    }

}
