package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack

class ToolTalismanWoodPeck(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = toolClasses
    override fun isHarvestCustom(stack: ItemStack, state: IBlockState) = true
    override fun getCustomHarvest(stack: ItemStack, state: IBlockState) = state.material == Material.WOOD

    companion object {
        val toolClasses = setOf("axe")
    }
}
