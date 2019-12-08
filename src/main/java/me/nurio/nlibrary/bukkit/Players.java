package me.nurio.nlibrary.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class Players {

    public static Collection<? extends Player> onlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }

    public static boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null;
    }

    public static boolean isOnline(String playerName) {
        return Bukkit.getPlayer(playerName) != null;
    }

}