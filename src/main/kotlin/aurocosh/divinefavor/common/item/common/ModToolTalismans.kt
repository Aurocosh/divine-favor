package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.*
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.item.tool_talismans.break_blocks.*
import aurocosh.divinefavor.common.item.tool_talismans.destroy.ToolTalismanDestroyBlocks
import aurocosh.divinefavor.common.item.tool_talismans.destroy.ToolTalismanDestroyCuboid
import aurocosh.divinefavor.common.item.tool_talismans.destroy.ToolTalismanDestroySide
import aurocosh.divinefavor.common.item.tool_talismans.destroy.ToolTalismanDestroySurface
import aurocosh.divinefavor.common.lib.extensions.isIce
import aurocosh.divinefavor.common.lib.extensions.isLava
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.block.Block
import net.minecraft.init.Blocks

object ModToolTalismans {
    // New fields
    lateinit var void_tool: ItemToolTalisman
    lateinit var aquatic_tool: ItemToolTalisman
    lateinit var molten_tool: ItemToolTalisman
    lateinit var break_blocks: ItemToolTalisman
    lateinit var memory_tool: ItemToolTalisman
    lateinit var fell_tree: ItemToolTalisman
    lateinit var obsidian_carving: ItemToolTalisman
    lateinit var ice_carving: ItemToolTalisman
    lateinit var volcanic_glass_cutter: ItemToolTalisman
    lateinit var ground_pick: ItemToolTalisman
    lateinit var wood_peck: ItemToolTalisman
    lateinit var break_side: ItemToolTalisman
    lateinit var break_surface: ItemToolTalisman
    lateinit var break_radius: ItemToolTalisman
    lateinit var destroy_blocks: ItemToolTalisman
    lateinit var destroy_surface: ItemToolTalisman
    lateinit var destroy_side: ItemToolTalisman
    lateinit var destroy_cuboid: ItemToolTalisman

    fun preInit() {
        // arbow;

        // blizrabi;
        aquatic_tool = ToolTalismanAquaticTool("aquatic_tool", ModSpirits.blizrabi, ConfigTool.aquaticTool.favorCost)
        ice_carving = ToolTalismanCarving("ice_carving", ModSpirits.blizrabi, ConfigTool.iceCarving.favorCost, Blocks.ICE, UtilPredicate.or(Block::isWater, Block::isIce))

        // endererer;

        // loon;

        // materia;

        // neblaze;
        destroy_blocks = ToolTalismanDestroyBlocks("destroy_blocks", ModSpirits.neblaze)
        destroy_surface = ToolTalismanDestroySurface("destroy_surface", ModSpirits.neblaze)
        destroy_side = ToolTalismanDestroySide("destroy_side", ModSpirits.neblaze)
        destroy_cuboid = ToolTalismanDestroyCuboid("destroy_cuboid", ModSpirits.neblaze)

        molten_tool = ToolTalismanMoltenTool("molten_tool", ModSpirits.neblaze, ConfigTool.moltenTool.favorCost)
        obsidian_carving = ToolTalismanObsidianCarving("obsidian_carving", ModSpirits.neblaze, ConfigTool.obsidianCarving.favorCost, Blocks.OBSIDIAN, Block::isLava)

        // redwind;
        memory_tool = ToolTalismanMemoryTool("memory_tool", ModSpirits.redwind, ConfigTool.memoryTool.favorCost)

        // romol;

        // squarefury;

        // New instances
        void_tool = ToolTalismanVoidTool("void_tool", ModSpirits.neblaze, ConfigTool.voidTool.favorCost)
        break_blocks = ToolTalismanBreakBlocks("break_blocks", ModSpirits.romol)
        fell_tree = ToolTalismanFellTree("fell_tree", ModSpirits.romol)
        volcanic_glass_cutter = ToolTalismanVolcanicGlassCutter("volcanic_glass_cutter", ModSpirits.romol, ConfigTool.volcanicGlassCutter.favorCost)
        ground_pick = ToolTalismanGroundPick("ground_pick", ModSpirits.romol, ConfigTool.groundPick.favorCost)
        wood_peck = ToolTalismanWoodPeck("wood_peck", ModSpirits.romol, ConfigTool.woodPeck.favorCost)
        break_side = ToolTalismanBreakSide("break_side", ModSpirits.romol)
        break_surface = ToolTalismanBreakSurface("break_surface", ModSpirits.romol)
        break_radius = ToolTalismanBreakRadius("break_radius", ModSpirits.romol)
    }
}
