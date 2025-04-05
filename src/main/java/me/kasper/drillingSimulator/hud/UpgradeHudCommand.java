package me.kasper.drillingSimulator.hud;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpgradeHudCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        UpgradeHud pg = new UpgradeHud();
        if (!(sender instanceof Player)) {
            sender.sendMessage("§lЭту команду может выполнить только игрок!");
        }

        Player player = (Player) sender;

        pg.open(player);

        return true;
    }
}
