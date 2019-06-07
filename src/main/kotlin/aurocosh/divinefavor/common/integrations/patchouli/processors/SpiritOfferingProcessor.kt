package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider
import vazkii.patchouli.common.util.ItemStackUtil

class SpiritOfferingProcessor : IComponentProcessor {
    var count: Int = 0
    var offering: Item? = null

    override fun setup(variables: IVariableProvider<String>) {
        val spiritName = variables.get("spirit")
        val spirit = ModMappers.spirits[ResourceLocation(spiritName)]
        if (spirit == null) {
            DivineFavor.logger.error("Spirit not found:$spiritName")
            return
        }

        offering = spirit.offering
        count = spirit.offeringCount
    }

    override fun process(key: String): String? {
        val offering = offering ?: return null

        if (key.startsWith("offering")) {
            val stack = ItemStack(offering, count)
            return ItemStackUtil.serializeStack(stack)
        } else if (key == "text") {
            return I18n.format("divinefavor:spirit_offering", count)
        }
        return null
    }

}