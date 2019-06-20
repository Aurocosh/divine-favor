package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class ToolTalismanVolcanicGlassCutter(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isDestroySpeedCustom(stack: ItemStack, state: IBlockState) = true
    override fun isHarvestCustom(stack: ItemStack, state: IBlockState) = true

    override fun getCustomDestroySpeed(stack: ItemStack, state: IBlockState): Float {
        if (state.block === Blocks.OBSIDIAN)
            return ConfigTool.volcanicGlassCutter.miningSpeed
        return super.getCustomDestroySpeed(stack, state)
    }

    override fun getCustomHarvest(stack: ItemStack, state: IBlockState): Boolean {
        if (state.block === Blocks.OBSIDIAN)
            return true
        return super.getCustomHarvest(stack, state)
    }
}
