package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.lib.builders.map.Maps;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

public class NetherSurge {
    @Config.Name("Favor cost")
    public int favorCost = 200;
    @Config.Name("Min neighbours to add")
    public int minNeighboursToAdd = 1;
    @Config.Name("Max neighbours to add")
    public int maxNeighboursToAdd = 3;
    @Config.Name("Min blocks to replace")
    public int minBlocksToReplace = 150;
    @Config.Name("Max blocks to replace")
    public int maxBlocksToReplace = 300;
    @Config.Name("Min enemies to spawn")
    public int minEnemiesToSpawn = 3;
    @Config.Name("Max enemies to spawn")
    public int maxEnemiesToSpawn = 10;
    @Config.Name("Enemy spawn radius")
    public int spawnRadius = 10;

    @Config.Name("Possible blocks")
    public Map<String, Double> possibleBlocks = Maps.<String, Double>builder()
            .put(Blocks.NETHERRACK.getRegistryName().toString(), 0.625d)
            .put(Blocks.SOUL_SAND.getRegistryName().toString(), 0.2d)
            .put(Blocks.LAVA.getRegistryName().toString(), 0.07d)
            .put(Blocks.QUARTZ_BLOCK.getRegistryName().toString(), 0.05d)
            .put(Blocks.GLOWSTONE.getRegistryName().toString(), 0.05d)
            .put(Blocks.GOLD_BLOCK.getRegistryName().toString(), 0.01d)
            .put(Blocks.DIAMOND_BLOCK.getRegistryName().toString(), 0.005d)
            .build();
}
