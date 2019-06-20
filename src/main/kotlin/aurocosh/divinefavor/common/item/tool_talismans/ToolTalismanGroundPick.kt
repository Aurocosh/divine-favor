package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack

class ToolTalismanGroundPick(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true

    override fun getCustomToolClasses(stack: ItemStack) = toolClasses

    companion object {
        val toolClasses = setOf("shovel")
    }
}
