package me.kasper.drillingSimulator.player.Listener;

import me.kasper.drillingSimulator.DrillingSimulator;
import me.kasper.drillingSimulator.map.MapManager;
import me.kasper.drillingSimulator.map.enums.BuildLocation;
import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinAndExitListener implements Listener {
    private final MapManager mapManager;

    public JoinAndExitListener(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @EventHandler
    public void of(PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage("");

        PlayerInfo existingInfo = PlayerInfo.of(player.getUniqueId());

        Location newLock = mapManager.freeLocation();

        if (existingInfo == null) {
            new PlayerInfo(player.getUniqueId());

            PlayerInfo.of(player.getUniqueId()).getDefaultDrill().setPlatformLocation(newLock);

            Bukkit.getScheduler().runTask(DrillingSimulator.getInstance(), () -> {
                mapManager.getMb().pasteStructure(BuildLocation.MAP_PLATFORM, newLock, false);

                PlayerInfo.of(player.getUniqueId()).getDefaultDrill().start();

                PlayerInfo.of(player.getUniqueId()).pickaxeEffect();
            });

            player.sendMessage("Welcome new user!");
        }else{
            PlayerInfo.of(player.getUniqueId()).getArmorStandManager().remove();

            player.sendMessage("Welcome bro!");

            player.sendMessage(existingInfo.toString());
        }

        PlayerInfo.of(player.getUniqueId()).getArmorStandManager().initializationStatistic();

        mapManager.teleportInSpawn(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent event){
        Player player = event.getPlayer();

        PlayerInfo.of(player.getUniqueId()).getArmorStandManager().remove();

        event.setQuitMessage(null);
    }

    @EventHandler
    public void on(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();

        PlayerInfo.of(player.getUniqueId()).pickaxeEffect();

        event.setDeathMessage("");

        player.spigot().respawn();

        mapManager.teleportInSpawn(player);
    }
}
