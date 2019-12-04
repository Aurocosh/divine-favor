package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.tool_talismans.DestroyBlocks;
import aurocosh.divinefavor.common.config.entries.tool_talismans.DestroyCuboid;
import aurocosh.divinefavor.common.config.entries.tool_talismans.DestroySide;
import aurocosh.divinefavor.common.config.entries.tool_talismans.DestroySurface;
import aurocosh.divinefavor.common.config.entries.tool_talismans.*;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/tool_talismans")
public class ConfigTool {
    @Config.Name("Break radius")
    public static BreakRadius breakRadius = new BreakRadius();
    // New configs
    @Config.Name("Silky tool")
    public static SilkyTool silkyTool = new SilkyTool();
    @Config.Name("Void tool")
    public static VoidTool voidTool = new VoidTool();
    @Config.Name("Aquatic tool")
    public static AquaticTool aquaticTool = new AquaticTool();
    @Config.Name("Molten tool")
    public static MoltenTool moltenTool = new MoltenTool();
    @Config.Name("Break blocks")
    public static BreakBlocks breakBlocks = new BreakBlocks();
    @Config.Name("Memory tool")
    public static MemoryTool memoryTool = new MemoryTool();
    @Config.Name("Fell tree")
    public static FellTree fellTree = new FellTree();
    @Config.Name("Obsidian carving")
    public static ObsidianCarving obsidianCarving = new ObsidianCarving();
    @Config.Name("Ice carving")
    public static IceCarving iceCarving = new IceCarving();
    @Config.Name("Volcanic glass cutter")
    public static VolcanicGlassCutter volcanicGlassCutter = new VolcanicGlassCutter();
    @Config.Name("Ground pick")
    public static GroundPick groundPick = new GroundPick();
    @Config.Name("Wood peck")
    public static WoodPeck woodPeck = new WoodPeck();
    @Config.Name("Break side")
    public static BreakSide breakSide = new BreakSide();
    @Config.Name("Break surface")
    public static BreakSurface breakSurface = new BreakSurface();
    @Config.Name("Destroy blocks")
    public static DestroyBlocks destroyBlocks = new DestroyBlocks();
    @Config.Name("Destroy surface")
    public static DestroySurface destroySurface = new DestroySurface();
    @Config.Name("Destroy side")
    public static DestroySide destroySide = new DestroySide();
    @Config.Name("Destroy cuboid")
    public static DestroyCuboid destroyCuboid = new DestroyCuboid();
}