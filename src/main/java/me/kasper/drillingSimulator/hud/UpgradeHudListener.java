package me.kasper.drillingSimulator.hud;

import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class UpgradeHudListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        UpgradeHud pg = new UpgradeHud();
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("§l§aПрокачки " + player.getDisplayName())) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String name = clicked.getItemMeta().getDisplayName();

            switch (name) {
                case "§l§aУлучшить бур":
                    PlayerInfo.of(player.getUniqueId()).upgradeDrillLvl();
                    player.closeInventory();
                    pg.open(player);
                    break;
                case "§l§aУлучшить респавн руд":
                    PlayerInfo.of(player.getUniqueId()).upgradeDrillOreSpawnSpeed();
                    player.closeInventory();
                    pg.open(player);
                    break;
                case "§l§aУлучшить цену руд":
                    PlayerInfo.of(player.getUniqueId()).upgradeOrePrice();
                    player.closeInventory();
                    pg.open(player);
                    break;
                case "§l§aУлучшить кирку":
                    PlayerInfo.of(player.getUniqueId()).upgradePickaxe();
                    player.closeInventory();
                    pg.open(player);
                    break;
                case "§l§aУлучшить сумку":
                    PlayerInfo.of(player.getUniqueId()).upgradeBag();
                    player.closeInventory();
                    pg.open(player);
                    break;
                default:
                    player.closeInventory();
            }
        }
    }
}
