package me.kasper.drillingSimulator.map;


import me.kasper.drillingSimulator.DrillingSimulator;
import me.kasper.drillingSimulator.map.enums.BuildLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MapBuild {

    public void pasteStructure(BuildLocation bl, Location targetLocation, boolean isConstruction) {
        Bukkit.getLogger().info(targetLocation.toString());

        int minX = Math.min(bl.getFirstLocation().getBlockX(), bl.getSecondLocation().getBlockX());
        int minY = Math.min(bl.getFirstLocation().getBlockY(), bl.getSecondLocation().getBlockY());
        int minZ = Math.min(bl.getFirstLocation().getBlockZ(), bl.getSecondLocation().getBlockZ());
        int maxX = Math.max(bl.getFirstLocation().getBlockX(), bl.getSecondLocation().getBlockX());
        int maxY = Math.max(bl.getFirstLocation().getBlockY(), bl.getSecondLocation().getBlockY());
        int maxZ = Math.max(bl.getFirstLocation().getBlockZ(), bl.getSecondLocation().getBlockZ());

        List<Material> blockTypes = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        World world = targetLocation.getWorld();

        if(isConstruction)
            targetLocation.subtract(0, (maxY - minY), 0);

        Bukkit.getLogger().info("Target location" + targetLocation.toString());

        for (int y = 0; y <= (maxY - minY); y++) {
            for (int x = (maxX - minX); x >= 0; x--) {
                for (int z = (maxZ - minZ); z >= 0; z--) {
                    Location fromLoc = new Location(world, minX + x, minY + y, minZ + z);
                    Block fromBlock = fromLoc.getBlock();

                    blockTypes.add(fromBlock.getType());
                    locations.add(new Location(world, targetLocation.getX() + x, targetLocation.getY() + y, targetLocation.getZ() + z));
                }
            }
        }


        for (int i = 0; i < blockTypes.size(); i++) {
            locations.get(i).getBlock().setType(blockTypes.get(i));
        }
    }

    public void cleanLastBuild(BuildLocation bl, Location targetLocation){
        World world = bl.getSecondLocation().getWorld();
        int minX = (int) Math.min(bl.getFirstLocation().getX(), bl.getSecondLocation().getX());
        int minY = (int) Math.min(bl.getFirstLocation().getY(), bl.getSecondLocation().getY());
        int minZ = (int) Math.min(bl.getFirstLocation().getZ(), bl.getSecondLocation().getZ());
        int maxX = (int) Math.max(bl.getFirstLocation().getX(), bl.getSecondLocation().getX());
        int maxY = (int) Math.max(bl.getFirstLocation().getY(), bl.getSecondLocation().getY());
        int maxZ = (int) Math.max(bl.getFirstLocation().getZ(), bl.getSecondLocation().getZ());

        List<Location> locations = new ArrayList<>();

        for (int y = 0; y <= (maxY - minY); y++) {
            for (int x = (maxX - minX); x >= 0; x--) {
                for (int z = (maxZ - minZ); z >= 0; z--) {
                    locations.add(new Location(world, targetLocation.getX() + x, targetLocation.getY() + y, targetLocation.getZ() + z));
                }
            }
        }

        for (int i = 0; i < locations.size(); i++) {
            locations.get(i).getBlock().setType(Material.AIR);
        }
    }
}
