package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider
import vazkii.patchouli.common.util.ItemStackUtil
import java.util.*

class ToolTalismanStatProcessor : IComponentProcessor {
    lateinit var bladeTalisman: ItemToolTalisman
    lateinit var itemStack: ItemStack

    override fun setup(variables: IVariableProvider<String>) {
        val talismanName = variables.get("talisman")
        val item = Item.REGISTRY.getObject(ResourceLocation(talismanName))
        if (item is ItemToolTalisman) {
            bladeTalisman = item
            itemStack = ItemStack(item)
        } else
            DivineFavor.logger.error("Tool talisman not found:$talismanName")
    }

    override fun process(key: String): String? {
        if (itemStack.isEmpty)
            return null

        when {
            key.startsWith("talisman") -> {
                return ItemStackUtil.serializeStack(itemStack)
            }
            key == "spirit_icon" -> {
                val spirit = bladeTalisman.spirit
                return spirit.icon.toString()
            }
            key == "spirit_symbol" -> {
                val spirit = bladeTalisman.spirit
                return spirit.symbol.toString()
            }
            key == "text" -> {
                val lines = ArrayList<String>()
                lines.add("Favor: " + bladeTalisman.spirit.name)
                val favorCost = bladeTalisman.getApproximateFavorCost(itemStack)
                if (favorCost == 0)
                    lines.add("No cost")
                else
                    lines.add("Favor cost: $favorCost")
                return lines.joinToString("$(br)")
            }
            else -> return null
        }
    }
}