package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

public class InfernalTouch {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Block conversions")
    public Map<String, String> blockConversions = new HashMap<String, String>() {
        {
            put("minecraft:netherrack", "minecraft:lava");
        }
    };

    @Config.Name("Material conversions")
    public Map<String, String> materialConversions = new HashMap<String, String>() {
        {
            put("rock", "minecraft:lava");
            put("wood", "minecraft:coal_block");
            put("grass", "minecraft:gravel");
            put("ground", "minecraft:gravel");
            put("sand", "minecraft:glass");
        }
    };
}
