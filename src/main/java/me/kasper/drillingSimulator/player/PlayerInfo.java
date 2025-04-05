package me.kasper.drillingSimulator.player;

import lombok.*;
import me.kasper.drillingSimulator.map.enums.BuildLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PlayerInfo {
    private UUID uuid;
    private String name;
    private int rebirth = 0;
    private long money = 0;
    private long totalOres = 0;
    private int bag = 0;

    private double oreSellBuster = 0.0;
    private double bagSizeBuster = 0.0;

    private int drillUpgradeSpawnOreSpeed = 1;
    private int drillUpgradeOrePrice = 1;
    private int drillUpgradeDrillLvl = 1;
    private int pickLevel = 1;
    private int bagLevel = 1;

    private int rebirthPrice = 25000;
    private int pointPrice = 5;
    private int resetOreTime = 60;
    private int oreCountSpawn = 10;
    private int bagSlot = 20;
    private int defaultUpgradePrice = 500;

    private PlayerDrill defaultDrill;
    private ArmorStandStatistic armorStandManager;

    public PlayerInfo(UUID uuid){
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(uuid).getDisplayName();
        this.defaultDrill = new PlayerDrill();
        this.armorStandManager = new ArmorStandStatistic(uuid);
        PlayerManager.registerToUUIDPlayer(uuid, this);
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    //ore
    public boolean addPointInBag(int point) {
        bag += point;
        if (bag > bagSlot) {
            bag = bagSlot;
            getPlayer().sendMessage("§l§cСумка заполнена " + bag + "/" + bagSlot);
            return false;
        }
        getPlayer().sendMessage("§l§e" + bag + "/" + bagSlot);
        return true;
    }

    public void cleanBag(){
        money += oreFinalPrice(bag);
        bag = 0;
        getPlayer().sendMessage( "§l" + bag + "/" + bagSlot);
    }

    //Final int is used buster
    public int oreFinalPrice(int point){
        return (int) ((pointPrice * point) * (1 + (0.1 * getDrillUpgradeOrePrice()) + (0.2 * rebirth) + oreSellBuster));
    }

    public void bagSlotSize(){
        bagSlot = (int) (bagSlot * (1 + (5 * getBagLevel() + bagSizeBuster)));
    }

    public void pickaxeEffect(){
        getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
        getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);

        if (getPickLevel() < 5) {
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 0, false, false));
        } else if (getPickLevel() >= 10 && getPickLevel() <= 20) {
            int hasteLevel = (getPickLevel() - 10) / 5;
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, hasteLevel, false, false));
        }
    }

    public int finalPrice(int lvl){
        return lvl == 1?defaultUpgradePrice: (int) (defaultUpgradePrice * lvl * (1.5 + getRebirth()));
    }

    // Static Edit
    public void addRebirth(){
        int price = rebirth * (2 + (rebirth / 10));

        if(rebirth > 100){
            getPlayer().sendMessage("Max Rebirth");
            return;
        }

        if(price <= money) {
            rebirth = rebirth + 1;

            createRebirth();
            money = 0;
            bag = 0;
            bagSlot = 20;

            pickaxeEffect();
        }else
            getPlayer().sendMessage(money + "/" + price);

    }

    public void createRebirth(){
        drillUpgradeSpawnOreSpeed = 1;
        drillUpgradeOrePrice = 1;
        pickLevel = 1;
        bagLevel = 1;
    }

    public void addOres(){
        totalOres = totalOres + 1;
    }

    // Upgrade
    private void addUpgradeSpawnOreSpeed() {
        drillUpgradeSpawnOreSpeed++;
    }

    private void addUpgradeOrePrice() {
        drillUpgradeOrePrice++;
    }

    private void addUpgradeDrillLvl() {
        drillUpgradeDrillLvl++;
    }

    private void addUpgradePickaxe() {
        pickLevel++;
    }

    private void addUpgradeBag() {
        bagLevel++;
    }

    public void upgradePickaxe() {
        int price = finalPrice(getPickLevel());

        if(getPickLevel() > 20){
            getPlayer().sendMessage("Max level");
            return;
        }

        if(price <= money) {
            money -= price;
            addUpgradePickaxe();
            pickaxeEffect();
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }else {
            getPlayer().sendMessage(money + "/" + price);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    public void upgradeDrillLvl() {
        int price =  finalPrice(getDrillUpgradeDrillLvl());

        if(getDrillUpgradeDrillLvl() > 20){
            getPlayer().sendMessage("Max level");
            return;
        }

        if(price <= money) {
            money -= price;
            addUpgradeDrillLvl();

            switch (getDrillUpgradeDrillLvl()){
                case 5:
                    defaultDrill.updateDrill(BuildLocation.MAP_DRILL_LVL1);
                    getPlayer().sendMessage("Бур эвалицианировал.");
                    break;
                case 10:
                    defaultDrill.updateDrill(BuildLocation.MAP_DRILL_LVL2);
                    getPlayer().sendMessage("Бур эвалицианировал.");
                    break;
                default:
                    getPlayer().sendMessage("Бур успешно прокачен.");
                    break;
            }
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }else {
            getPlayer().sendMessage(money + "/" + price);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    public void upgradeDrillOreSpawnSpeed() {
        int price = finalPrice(getDrillUpgradeSpawnOreSpeed());

        if(getDrillUpgradeSpawnOreSpeed() > 20){
            getPlayer().sendMessage("Max level");
            return;
        }

        if(price <= money) {
            money -= price;
            addUpgradeSpawnOreSpeed();
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }else {
            getPlayer().sendMessage(money + "/" + price);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    public void upgradeOrePrice() {
        int price = finalPrice(getDrillUpgradeOrePrice());

        if(getDrillUpgradeOrePrice() > 20){
            getPlayer().sendMessage("Max level");
            return;
        }

        if(price <= money) {
            money -= price;
            addUpgradeOrePrice();
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }else {
            getPlayer().sendMessage(money + "/" + price);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    public void upgradeBag() {
        int price = finalPrice(getBagLevel());

        if(getBagLevel() >20){
            getPlayer().sendMessage("Max level");
            return;
        }

        if(price <= money) {
            money -= price;
            addUpgradeBag();
            bagSlotSize();
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }else {
            getPlayer().sendMessage(money + "/" + price);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    public static PlayerInfo of(UUID uuid) {
        return PlayerManager.getPlayer(uuid);
    }

    @Override
    public String toString() {
        return "§l§aName - " + name + "\n" +
                "Money - " + money + "\n" +
                "Rebirth - " + rebirth + "\n" +
                "Ors in all time - " + totalOres + "\n" +
                "\n" +
                "Drill Speed Upgrade: " + drillUpgradeSpawnOreSpeed + "\n" +
                "Ore Sell Boost: " + drillUpgradeOrePrice + "\n" +
                "Pickaxe Level: " + pickLevel + "\n" +
                "Bag Level: " + bagLevel;
    }
}
