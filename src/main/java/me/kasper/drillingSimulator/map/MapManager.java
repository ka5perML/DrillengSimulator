package me.kasper.drillingSimulator.map;

import lombok.Getter;
import me.kasper.drillingSimulator.map.enums.MapLocation;
import me.kasper.drillingSimulator.player.PlayerInfo;
import me.kasper.drillingSimulator.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MapManager implements Listener {
    private int spawnIndex = PlayerManager.playerCount();

    @Getter
    private MapBuild mb = new MapBuild();

    public Location freeLocation(){
        World world = MapLocation.START_LOCK.getLocation().getWorld();

        int x = (int) MapLocation.START_LOCK.getLocation().getX() + (spawnIndex / 10) * 200;
        int y = (int) MapLocation.START_LOCK.getLocation().getY();
        int z = (int) MapLocation.START_LOCK.getLocation().getZ() + (spawnIndex % 10) * 200;

        spawnIndex++;

        Location newLocation = new Location(world, x, y, z);

        return newLocation;
    }

    public void teleportInLock(Player player, Location location) {
        Location tpLoc = location.add(MapLocation.SPAWN_LOCK.getLocation());
        player.teleport(new Location(tpLoc.getWorld(), tpLoc.getX(), 200, tpLoc.getBlockZ()));
    }

    public void teleportInSpawn(Player player) {
        Location tpLoc = PlayerInfo.of(player.getUniqueId()).getDefaultDrill().getPlatformLocation().clone().add(MapLocation.SPAWN_LOCK.getLocation());
        tpLoc.setY(204);
        tpLoc.setYaw(MapLocation.SPAWN_LOCK.getLocation().getYaw());
        player.teleport(tpLoc);
    }
}
