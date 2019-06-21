package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.item.tool_talismans.base.PickDestroySpeedType
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class ToolTalismanVolcanicGlassCutter(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isHarvestCustom(stack: ItemStack, state: IBlockState) = true

    override fun getCustomHarvest(stack: ItemStack, state: IBlockState): Boolean {
        if (state.block === Blocks.OBSIDIAN)
            return true
        return super.getCustomHarvest(stack, state)
    }

    override fun getDestroySpeedType(stack: ItemStack, state: IBlockState): PickDestroySpeedType {
        if (state.block === Blocks.OBSIDIAN)
            return PickDestroySpeedType.GET_FROM_TALISMAN
        return PickDestroySpeedType.STANDARD
    }

    override fun getCustomDestroySpeed(talismanStack: ItemStack, state: IBlockState): Float {
        return ConfigTool.volcanicGlassCutter.miningSpeed
    }
}
