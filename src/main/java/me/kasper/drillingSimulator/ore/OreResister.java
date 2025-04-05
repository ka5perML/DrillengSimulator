package me.kasper.drillingSimulator.ore;

import me.kasper.drillingSimulator.DrillingSimulator;
import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.kasper.drillingSimulator.ore.listener.OreListener.oreMap;

public class OreResister {
    private Map<Player, Integer> playerTimeOutMap = new HashMap<>();

    public void reset(){
        Bukkit.getScheduler().runTaskTimer(DrillingSimulator.getInstance(), new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                if(oreMap.isEmpty() || oreMap == null) return;

                List<Player> toRemove = new ArrayList<>();

                oreMap.forEach(((player, oreData) -> {
                    if (!playerTimeOutMap.containsKey(player)) {
                        playerTimeOutMap.put(player, time);
                    }

                    if (time == playerTimeOutMap.get(player) + PlayerInfo.of(player.getUniqueId()).getResetOreTime()) {
                        oreData.forEach(ore -> {
                            ore.getLocation().getBlock().setType(ore.getMaterial());
                        });

                        player.sendRawMessage("Руды обновились");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                        toRemove.add(player);
                    }
                }));

                toRemove.forEach(player -> {
                    oreMap.remove(player);
                    playerTimeOutMap.remove(player);
                });

                time++;
            }
        },0,20);
    }
}
