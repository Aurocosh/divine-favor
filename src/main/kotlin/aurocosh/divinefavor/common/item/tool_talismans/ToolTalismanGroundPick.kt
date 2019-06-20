package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack

class ToolTalismanGroundPick(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = toolClasses
    override fun isHarvestCustom(stack: ItemStack, state: IBlockState) = true
    override fun getCustomHarvest(stack: ItemStack, state: IBlockState): Boolean {
        val material = state.material
        return material == Material.GROUND || material == Material.CLAY || material == Material.PLANTS || material == Material.CACTUS || material == Material.SAND || material == Material.SNOW
    }

    companion object {
        val toolClasses = setOf("shovel")
    }
}
