package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
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
    public static int talismanCastDistance = 48;
    @Config.Name("Max climbing radius")
    public static int maxClimbingRadius = 12;

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