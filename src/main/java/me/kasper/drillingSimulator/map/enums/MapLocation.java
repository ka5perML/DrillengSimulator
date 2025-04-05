package me.kasper.drillingSimulator.map.enums;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum MapLocation {
    START_LOCK(new Location(Bukkit.getWorld("world"), 200, 0, 200)),

    SPAWN_LOCK(new Location(Bukkit.getWorld("world"), 31.5, 3, 16.5, 90,0)),

    DRILL_LOCK(new Location(Bukkit.getWorld("world"), 6, 200, 8)),

    STATISTIC_LOCK(new Location(Bukkit.getWorld("world"), 9, 202, 1));

    @Getter
    private Location location;

    MapLocation(Location world) {
        this.location = world;
    }

    public Location getTrueLocation(Location midLocation){
        return  midLocation.add(this.location);
    }
}
