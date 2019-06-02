package com.cavepvp.skywars.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeUtil {

    public static void freeze(Player p) {
        p.setWalkSpeed(0.0F);
        p.setFlySpeed(0.0F);
        p.setSprinting(false);
        p.setFoodLevel(0);
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
    }

    public static void unfreezePlayer(Player p) {
        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);
        p.setSprinting(true);
        p.setFoodLevel(20);
        p.removePotionEffect(PotionEffectType.JUMP);
        p.closeInventory();
    }
}
