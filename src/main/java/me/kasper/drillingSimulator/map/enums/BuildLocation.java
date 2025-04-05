package me.kasper.drillingSimulator.map.enums;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum BuildLocation {
    MAP_PLATFORM(new Location(Bukkit.getWorld("world"), -7, 0, -20),
            new Location(Bukkit.getWorld("world"), 29, 255, 2)),

    MAP_DRILL(
            new Location(Bukkit.getWorld("world"), 1, 86, 28), // second
            new Location(Bukkit.getWorld("world"), 7, 99, 21), //first
            new Location(Bukkit.getWorld("world"), 3, 0, 4)),  //central

    MAP_DRILL_LVL1(
            new Location(Bukkit.getWorld("world"), 0, 78, 41),
            new Location(Bukkit.getWorld("world"), 9, 88, 32),
            new Location(Bukkit.getWorld("world"), 3, 0, 4)),

    MAP_DRILL_LVL2(
            new Location(Bukkit.getWorld("world"), 0, 71, 52),
            new Location(Bukkit.getWorld("world"), 9, 88, 43),
            new Location(Bukkit.getWorld("world"), 3, 0, 4));

    @Getter
    Location firstLocation;
    @Getter
    Location secondLocation;
    @Getter
    Location center;

    BuildLocation(Location firstLock, Location secondLocation){
        this.firstLocation = firstLock;
        this.secondLocation = secondLocation;
    }

    BuildLocation(Location firstLock, Location secondLocation, Location center){
        this.firstLocation = firstLock;
        this.secondLocation = secondLocation;
        this.center = center;
    }
}
