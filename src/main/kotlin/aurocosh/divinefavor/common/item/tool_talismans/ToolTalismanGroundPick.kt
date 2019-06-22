package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanGroundPick(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = toolClasses

    override fun canHarvest(stack: ItemStack, event: PlayerEvent.HarvestCheck) {
        val material = event.targetBlock.material
        if (material == Material.GROUND || material == Material.CLAY || material == Material.PLANTS || material == Material.CACTUS || material == Material.SAND || material == Material.SNOW)
            event.setCanHarvest(true)
    }

    companion object {
        val toolClasses = setOf("shovel")
    }
}
