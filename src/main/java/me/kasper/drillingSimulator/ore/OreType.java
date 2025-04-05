package me.kasper.drillingSimulator.ore;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum OreType {
    COAL_ORE(Material.COAL_ORE, 1, 1),
    IRON_ORE(Material.IRON_ORE, 3,5),
    GOLD_ORE(Material.GOLD_ORE, 5,7),
    DIAMOND_ORE(Material.DIAMOND_ORE, 10,13);

    private final Material material;
    private final int points;
    private final int oreOpenLvl;

    OreType(Material material, int points, int oreOpenLvl) {
        this.material = material;
        this.points = points;
        this.oreOpenLvl = oreOpenLvl;
    }

    public static int getPointsForMaterial(Material material) {
        for (OreType ore : values()) {
            if (ore.getMaterial() == material) {
                return ore.getPoints();
            }
        }
        return 0;
    }
}