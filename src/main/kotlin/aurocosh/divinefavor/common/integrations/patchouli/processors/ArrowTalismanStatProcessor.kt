package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider
import vazkii.patchouli.common.util.ItemStackUtil
import java.text.DecimalFormat
import java.util.*

class ArrowTalismanStatProcessor : IComponentProcessor {
    lateinit var arrowTalisman: ItemArrowTalisman

    override fun setup(variables: IVariableProvider<String>) {
        val talismanName = variables.get("talisman")
        val item = Item.REGISTRY.getObject(ResourceLocation(talismanName))
        if (item is ItemArrowTalisman)
            arrowTalisman = item
        else
            DivineFavor.logger.error("Arrow talisman not found:$talismanName")
    }

    override fun process(key: String): String? {
        when {
            key.startsWith("talisman") -> {
                val stack = ItemStack(arrowTalisman, 1)
                return ItemStackUtil.serializeStack(stack)
            }
            key == "spirit_icon" -> {
                val spirit = arrowTalisman.spirit
                return spirit.icon.toString()
            }
            key == "spirit_symbol" -> {
                val spirit = arrowTalisman.spirit
                return spirit.symbol.toString()
            }
            key == "text" -> {
                val lines = ArrayList<String>()
                lines.add("Favor: " + arrowTalisman.spirit.name)
                val favorCost = arrowTalisman.favorCost
                if (favorCost == 0)
                    lines.add("No cost")
                else
                    lines.add("Favor cost: $favorCost")
                val arrowDamage = arrowTalisman.arrowDamage
                if (arrowDamage == 0.0)
                    lines.add("Arrow deals no damage")
                else {
                    val formatter = DecimalFormat("#0.00")
                    lines.add("Arrow damage: " + formatter.format(arrowDamage))
                }
                val options = arrowTalisman.options
                if (options == ArrowOptions.NORMAL)
                    lines.add("No extra properties")
                else {
                    lines.add("Extra properties: ")
                    if (options.contains(ArrowOptions.BreakOnHit))
                        lines.add("  Will break on hit. ")
                    if (options.contains(ArrowOptions.RequiresTarget))
                        lines.add("  Needs target.")
                }
                return lines.joinToString("$(br)")
            }
            else -> return null
        }
    }

}