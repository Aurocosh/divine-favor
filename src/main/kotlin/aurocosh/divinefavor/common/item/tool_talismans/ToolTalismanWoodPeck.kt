package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanWoodPeck(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = toolClasses

    override fun canHarvest(stack: ItemStack, event: PlayerEvent.HarvestCheck) {
        if (event.targetBlock.material == Material.WOOD)
            event.setCanHarvest(true)
    }

    companion object {
        val toolClasses = setOf("axe")
    }
}
