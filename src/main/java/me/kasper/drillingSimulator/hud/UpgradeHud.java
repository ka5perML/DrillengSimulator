package me.kasper.drillingSimulator.hud;

import me.kasper.drillingSimulator.player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class UpgradeHud {
    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54,  "§l§aПрокачки " + player.getDisplayName());

        ItemStack drillUpgrade = createItem(Material.IRON_PICKAXE,
                "§l§aУлучшить бур", Arrays.asList(
                        "Уровень " + PlayerInfo.of(player.getUniqueId()).getDrillUpgradeDrillLvl(),
                        "Цена " + PlayerInfo.of(player.getUniqueId()).finalPrice(PlayerInfo.of(player.getUniqueId()).getDrillUpgradeDrillLvl())));

        ItemStack speedUpgrade = createItem(Material.SUGAR,
                "§l§aУлучшить респавн руд", Arrays.asList(
                        "Уровень " + PlayerInfo.of(player.getUniqueId()).getDrillUpgradeSpawnOreSpeed(),
                        "Цена " + PlayerInfo.of(player.getUniqueId()).finalPrice(PlayerInfo.of(player.getUniqueId()).getDrillUpgradeSpawnOreSpeed())));

        ItemStack priceUpgrade = createItem(Material.DIAMOND,
                "§l§aУлучшить цену руд", Arrays.asList(
                        "Уровень " + PlayerInfo.of(player.getUniqueId()).getDrillUpgradeOrePrice(),
                        "Цена " + PlayerInfo.of(player.getUniqueId()).finalPrice(PlayerInfo.of(player.getUniqueId()).getDrillUpgradeOrePrice())));

        ItemStack pickaxeUpgrade = createItem(Material.WOOD_PICKAXE,
                "§l§aУлучшить кирку", Arrays.asList(
                        "Уровень " + PlayerInfo.of(player.getUniqueId()).getPickLevel(),
                        "Цена " + PlayerInfo.of(player.getUniqueId()).finalPrice(PlayerInfo.of(player.getUniqueId()).getPickLevel())));

        ItemStack bagUpgrade = createItem(Material.CHEST,
                "§l§aУлучшить сумку", Arrays.asList(
                        "Уровень " + PlayerInfo.of(player.getUniqueId()).getBagLevel(),
                        "Цена " + PlayerInfo.of(player.getUniqueId()).finalPrice(PlayerInfo.of(player.getUniqueId()).getBagLevel())));


        inv.setItem(10, drillUpgrade);
        inv.setItem(12, speedUpgrade);
        inv.setItem(14, priceUpgrade);
        inv.setItem(16, pickaxeUpgrade);
        inv.setItem(31, bagUpgrade);

        player.openInventory(inv);
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
