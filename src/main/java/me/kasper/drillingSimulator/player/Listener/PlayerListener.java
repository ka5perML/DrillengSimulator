package me.kasper.drillingSimulator.player.Listener;

import me.kasper.drillingSimulator.DrillingSimulator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener {
    public PlayerListener(){
        World world = Bukkit.getWorld("world");

        world.setSpawnFlags(false, false);

        Bukkit.getScheduler().runTaskTimer(DrillingSimulator.getInstance(), () -> {
            world.setTime(1000);
            world.setStorm(false);
            Bukkit.getOnlinePlayers().forEach(player ->  player.setFoodLevel(20));
        },0, 20);
    }

    @EventHandler
    public void on(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getInventory().clear();

        player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_PICKAXE));

        player.setHealth(20);

        player.setFoodLevel(20);
    }
}
