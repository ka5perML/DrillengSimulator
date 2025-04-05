package me.kasper.drillingSimulator.ore.listener;

import me.kasper.drillingSimulator.ore.OreData;
import me.kasper.drillingSimulator.ore.OreResister;
import me.kasper.drillingSimulator.ore.OreType;
import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;

public class OreListener implements Listener {
    private OreResister or;

    public OreListener(){
        or = new OreResister();
        or.reset();
    }

    private final List<Material> VALID_ORES = Arrays.asList(
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE
    );

    public static Map<Player, List<OreData>> oreMap = new HashMap<>();

    @EventHandler
    public void on(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
            return;
        } else if(checkValid(block)){
            if(PlayerInfo.of(player.getUniqueId()).addPointInBag(OreType.getPointsForMaterial(block.getType()))){
                oreMap.computeIfAbsent(player, k -> new ArrayList<>()).add(new OreData(block.getType(), block.getLocation()));
                block.setType(Material.COBBLESTONE);
                PlayerInfo.of(player.getUniqueId()).addOres();
                event.setCancelled(true);
            }
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
    }

    private boolean checkValid(Block block){
        return VALID_ORES.contains(block.getType());
    }

}
