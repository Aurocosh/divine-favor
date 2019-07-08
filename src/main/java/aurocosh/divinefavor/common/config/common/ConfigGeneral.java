package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;

import java.util.HashSet;
import java.util.Set;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/general")
public class ConfigGeneral {
    @Config.Name("Particle limit by type")
    public static int particleLimit = 5000;
    @Config.Name("Particle radius")
    public static int particleRadius = 32;
    @Config.Name("Talisman cast distance")
    public static int talismanCastDistance = 64;
    @Config.Name("Max climbing radius")
    public static int maxClimbingRadius = 12;
    @Config.Name("Time sync period")
    public static int timeSyncPeriod = UtilTick.INSTANCE.secondsToTicks(1);
    @Config.Name("Temporary template limit")
    public static int temporaryTemplateLimit = 5000;
    @Config.Name("Persistent template limit")
    public static int persistentTemplateLimit = 50000;

    @Config.Name("Block breaking cost")
    public static int blockBreakingCost = 8;
    @Config.Name("Block destruction cost")
    public static int blockDestructionCost = 2;
    @Config.Name("Block replacing cost")
    public static int blockReplacingCost = 4;
    @Config.Name("Block building cost")
    public static int blockBuildingCost = 1;

    @Config.Name("Ore blocks")
    public static String[] oreBlocks = new String[]{
            "minecraft:gold_ore",
            "minecraft:iron_ore",
            "minecraft:coal_ore",
            "minecraft:lapis_ore",
            "minecraft:diamond_ore",
            "minecraft:redstone_ore",
            "minecraft:emerald_ore",
    };

    @Config.Ignore
    public static final Set<Block> ORE_BLOCKS = new HashSet<>();
}