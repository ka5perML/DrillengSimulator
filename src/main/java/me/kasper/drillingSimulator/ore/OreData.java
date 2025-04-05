package me.kasper.drillingSimulator.ore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OreData {
    private Material material;
    private Location location;
}
