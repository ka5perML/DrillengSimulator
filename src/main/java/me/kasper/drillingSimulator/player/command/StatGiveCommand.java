package me.kasper.drillingSimulator.player.command;

import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§lЭту команду может выполнить только игрок!");
            return true;
        }

        if(args.length == 0){
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("addRebirth")) {
            if (args.length == 1) {
                return false;
            }

            try {
                int rebirths = Integer.parseInt(args[1]);
                if(rebirths >= 100){
                    player.sendMessage("§lМаксимальный ребитх: 100");
                    rebirths = 100;
                    PlayerInfo.of(player.getUniqueId()).setRebirth(rebirths);
                    return true;
                }
                PlayerInfo.of(player.getUniqueId()).setRebirth(rebirths);
                player.sendMessage("§lКоличество перерождений установлено: " + rebirths);
                return true;
            } catch (NumberFormatException e) {
                PlayerInfo.of(player.getUniqueId()).addRebirth();
                return true;
            }
        } else if (args[0].equalsIgnoreCase("stats")) {
            player.sendMessage(PlayerInfo.of(player.getUniqueId()).toString());
            return true;
        }else if (args[0].equalsIgnoreCase("drillUpgrade")) {
            try {
                int lvl = PlayerInfo.of(player.getUniqueId()).getDrillUpgradeDrillLvl();
                if(lvl >= 20){
                    player.sendMessage("§lМаксимальный уровень: 20");
                    return true;
                }

                PlayerInfo.of(player.getUniqueId()).upgradeDrillLvl();
                player.sendMessage("уровень: " + PlayerInfo.of(player.getUniqueId()).getDrillUpgradeDrillLvl());
                return true;
            } catch (NumberFormatException e) {
                PlayerInfo.of(player.getUniqueId()).addRebirth();
                return true;
            }
        }
        return false;
    }
}
