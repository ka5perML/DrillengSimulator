package me.kasper.drillingSimulator.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private static final Map<UUID, PlayerInfo> players = new HashMap<>();

    public static int playerCount(){
        return players.size();
    }

    public static void registerPlayer(Player player, PlayerInfo playerInfo) {
        players.put(player.getUniqueId(), playerInfo);
    }

    public static void registerToUUIDPlayer(UUID uuid, PlayerInfo playerInfo) {
        players.put(uuid, playerInfo);
    }

    public static PlayerInfo getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static void removePlayer(UUID uuid) {
        players.remove(uuid);
    }
}
