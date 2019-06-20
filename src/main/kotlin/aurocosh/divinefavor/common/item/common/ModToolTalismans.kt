package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.*
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.isIce
import aurocosh.divinefavor.common.lib.extensions.isLava
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.block.Block
import net.minecraft.init.Blocks

object ModToolTalismans {
    // New fields
    lateinit var obsidian_carving: ItemToolTalisman
    lateinit var ice_carving: ItemToolTalisman
    lateinit var volcanic_glass_cutter: ItemToolTalisman
    lateinit var ground_pick: ItemToolTalisman
    lateinit var wood_peck: ItemToolTalisman
    lateinit var break_side: ItemToolTalisman
    lateinit var break_surface: ItemToolTalisman
    lateinit var break_radius: ItemToolTalisman

    fun preInit() {
        // arbow;

        // blizrabi;

        // endererer;

        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // New instances
        obsidian_carving = ToolTalismanObsidianCarving("obsidian_carving", ModSpirits.neblaze, ConfigTool.obsidianCarving.favorCost,Blocks.OBSIDIAN, Block::isLava)
        ice_carving = ToolTalismanCarving("ice_carving", ModSpirits.blizrabi, ConfigTool.iceCarving.favorCost, Blocks.ICE, UtilPredicate.or(Block::isWater, Block::isIce))
        volcanic_glass_cutter = ToolTalismanVolcanicGlassCutter("volcanic_glass_cutter", ModSpirits.romol, ConfigTool.volcanicGlassCutter.favorCost)
        ground_pick = ToolTalismanGroundPick("ground_pick", ModSpirits.romol, ConfigTool.groundPick.favorCost)
        wood_peck = ToolTalismanWoodPeck("wood_peck", ModSpirits.romol, ConfigTool.woodPeck.favorCost)
        break_side = ToolTalismanBreakSide("break_side", ModSpirits.romol, ConfigTool.breakSide.favorCost)
        break_surface = ToolTalismanBreakSurface("break_surface", ModSpirits.romol, ConfigTool.breakSurface.favorCost)
        break_radius = ToolTalismanBreakRadius("break_radius", ModSpirits.romol, ConfigTool.breakRadius.favorCost)
    }
}
