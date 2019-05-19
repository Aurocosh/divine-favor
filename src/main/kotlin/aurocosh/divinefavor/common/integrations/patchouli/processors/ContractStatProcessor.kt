package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.contract.ItemFavorContract
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider

class ContractStatProcessor : IComponentProcessor {
    private var text: String? = null

    override fun setup(variables: IVariableProvider<String>) {
        val contractName = variables.get("contract")
        val item = Item.REGISTRY.getObject(ResourceLocation(contractName))
        if (item is ItemFavorContract) {
            text = I18n.format("item.divinefavor:contract.effects") + "$(br)"

            var effects = ""
            val regen = item.regen
            if (regen != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.regen", regen) + "$(br)"
            val min = item.min
            if (min != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.min", min) + "$(br)"
            val max = item.max
            if (max != 0)
                effects += "$(li)" + I18n.format("item.divinefavor:contract.max", max) + "$(br)"
            if (effects.isEmpty())
                effects += "$(li)" + I18n.format("item.divinefavor:contract.no_effects") + "$(br)"
            text += effects
        } else
            DivineFavor.logger.error("Contract not found:$contractName")
    }

    override fun process(key: String): String? {
        return if (key == "text") text else null
    }
}