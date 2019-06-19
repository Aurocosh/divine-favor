package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.ToolTalismanBreakRadius
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.ModSpirits

object ModToolTalismans {
    // New fields
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
        break_radius = ToolTalismanBreakRadius("break_radius", ModSpirits.romol, ConfigTool.breakRadius.favorCost)
    }
}
