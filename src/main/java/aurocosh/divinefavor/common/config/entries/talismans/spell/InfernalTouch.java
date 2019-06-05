package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.lib.builders.map.Maps;
import net.minecraftforge.common.config.Config;

import java.util.Map;

public class InfernalTouch {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Block conversions")
    public Map<String, String> blockConversions = Maps.<String, String>builder()
            .put("minecraft:netherrack", "minecraft:lava")
            .build();

    @Config.Name("Material conversions")
    public Map<String, String> materialConversions = Maps.<String, String>builder()
            .put("rock", "minecraft:lava")
            .put("wood", "minecraft:coal_block")
            .put("grass", "minecraft:gravel")
            .put("ground", "minecraft:gravel")
            .put("sand", "minecraft:glass")
            .build();

}
