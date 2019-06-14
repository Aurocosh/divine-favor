package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider
import vazkii.patchouli.common.util.ItemStackUtil

import java.util.ArrayList

class SpellTalismanStatProcessor : IComponentProcessor {
    lateinit var spellTalisman: ItemSpellTalisman
    lateinit var itemStack: ItemStack

    override fun setup(variables: IVariableProvider<String>) {
        val talismanName = variables.get("talisman")
        val item = Item.REGISTRY.getObject(ResourceLocation(talismanName))
        if (item is ItemSpellTalisman) {
            spellTalisman = item
            itemStack = ItemStack(item)
        } else
            DivineFavor.logger.error("Spell talisman not found:$talismanName")
    }

    override fun process(key: String): String? {
        if(itemStack.isEmpty)
            return null

        when {
            key.startsWith("talisman") -> return ItemStackUtil.serializeStack(itemStack)
            key == "spirit_icon" -> return spellTalisman.spirit.icon.toString()
            key == "spirit_symbol" -> return spellTalisman.spirit.symbol.toString()
            key == "text" -> {
                val lines = ArrayList<String>()
                val spirit = spellTalisman.spirit
                lines.add("Favor: " + spirit.name)
                val favorCost = spellTalisman.getFavorCost(itemStack)
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