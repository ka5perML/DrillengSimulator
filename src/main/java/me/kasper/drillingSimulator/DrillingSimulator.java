package me.kasper.drillingSimulator;

import lombok.Getter;
import me.kasper.drillingSimulator.hud.UpgradeHudCommand;
import me.kasper.drillingSimulator.hud.UpgradeHudListener;
import me.kasper.drillingSimulator.map.MapManager;
import me.kasper.drillingSimulator.player.Listener.JoinAndExitListener;
import me.kasper.drillingSimulator.ore.listener.OreListener;
import me.kasper.drillingSimulator.player.Listener.InteractivListener;
import me.kasper.drillingSimulator.player.Listener.PlayerListener;
import me.kasper.drillingSimulator.player.command.StatGiveCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DrillingSimulator extends JavaPlugin {
    @Getter
    private static DrillingSimulator instance;
    private MapManager mapManager;


    @Override
    public void onEnable() {
        instance = this;
        mapManager = new MapManager();

        //command
        getCommand("statistic").setExecutor(new StatGiveCommand());
        getCommand("menu").setExecutor(new UpgradeHudCommand());

        //listener
        registerListener(
                new JoinAndExitListener(mapManager),
                new OreListener(),
                new InteractivListener(),
                new UpgradeHudListener(),
                new PlayerListener()
        );

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§l§aRestart"));
    }

    private void registerListener(Listener... listeners){
        for(Listener l : listeners){
            Bukkit.getServer().getPluginManager().registerEvents(l, this);
        }
    }

}
