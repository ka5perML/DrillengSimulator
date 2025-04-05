package me.kasper.drillingSimulator.player;

import lombok.Getter;
import lombok.Setter;
import me.kasper.drillingSimulator.DrillingSimulator;
import me.kasper.drillingSimulator.map.MapBuild;
import me.kasper.drillingSimulator.map.enums.BuildLocation;
import me.kasper.drillingSimulator.map.enums.MapLocation;
import org.bukkit.*;

import java.util.Random;

@Setter
@Getter
public class PlayerDrill {
    private Location platformLocation;
    private Location drillCenter;
    private Location drillLoc;

    private final MapBuild mapBuild = new MapBuild();
    private final Random random = new Random();

    public void start() {
        this.drillLoc = platformLocation.clone().add(MapLocation.DRILL_LOCK.getLocation());
        this.drillCenter = drillLoc.clone().add(BuildLocation.MAP_DRILL.getCenter());

        spawnDrill();
        Bukkit.getLogger().info("Spawn drill" + drillLoc);
    }

    private void spawnDrill() {
        mapBuild.pasteStructure(BuildLocation.MAP_DRILL, drillLoc, false);
    }

    public void updateDrill(BuildLocation bb) {
        Bukkit.getScheduler().runTask(DrillingSimulator.getInstance(), () -> {
            mapBuild.cleanLastBuild(BuildLocation.MAP_DRILL, drillLoc);

            mapBuild.pasteStructure(bb, drillLoc.clone(), true);
        });
    }

}
