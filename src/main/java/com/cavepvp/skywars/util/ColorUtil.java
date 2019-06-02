package com.cavepvp.skywars.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }
}
