package me.kasper.drillingSimulator.player.Listener;

import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class InteractivListener implements Listener {

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (event.isSneaking()) {
            Block blockUnder = player.getLocation().subtract(0, 1, 0).getBlock();
            Material material = blockUnder.getType();

            if (material == Material.WOOL) {
                if(PlayerInfo.of(player.getUniqueId()).getBag() != 0){
                    PlayerInfo.of(player.getUniqueId()).cleanBag();
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            }
        }
    }
}
