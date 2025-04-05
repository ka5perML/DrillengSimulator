package me.kasper.drillingSimulator.player;

import lombok.Getter;
import lombok.Setter;
import me.kasper.drillingSimulator.DrillingSimulator;
import me.kasper.drillingSimulator.map.enums.MapLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@Getter
@Setter
public class ArmorStandStatistic {
    private ArmorStand armorStand;
    private UUID uuid;

    public ArmorStandStatistic(UUID uuid) {
        this.uuid = uuid;
    }

    public void initializationStatistic() {
        Location location = PlayerInfo.of(uuid).getDefaultDrill().getPlatformLocation().clone();

        Bukkit.getLogger().info("Spawn entity " + location.toString());
        spawnArmorStand(location);
        startUpdateTask();
    }

    private void spawnArmorStand(Location location) {
        armorStand = (ArmorStand) location.getWorld().spawnEntity(MapLocation.STATISTIC_LOCK.getTrueLocation(location), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setMarker(true);
        armorStand.setCustomName("Загрузка");
    }

    private void startUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.setCustomName(PlayerInfo.of(uuid).toString());
            }
        }.runTaskTimer(DrillingSimulator.getInstance(), 0, 20);
    }

    public void remove() {
        if (armorStand != null) {
            armorStand.remove();
        }
    }
}
