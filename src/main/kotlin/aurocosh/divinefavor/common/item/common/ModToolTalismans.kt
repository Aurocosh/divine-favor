package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.*
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.ModSpirits

object ModToolTalismans {
    // New fields
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
        wood_peck = ToolTalismanWoodPeck("wood_peck", ModSpirits.romol, ConfigTool.woodPeck.favorCost)
        break_side = ToolTalismanBreakSide("break_side", ModSpirits.romol, ConfigTool.breakSide.favorCost)
        break_surface = ToolTalismanBreakSurface("break_surface", ModSpirits.romol, ConfigTool.breakSurface.favorCost)
        break_radius = ToolTalismanBreakRadius("break_radius", ModSpirits.romol, ConfigTool.breakRadius.favorCost)
    }
}
