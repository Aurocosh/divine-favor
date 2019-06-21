package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.tool_talismans.*;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/tool_talismans")
public class ConfigTool {
    @Config.Name("Break radius")
    public static BreakRadius breakRadius = new BreakRadius();
    // New configs
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
}