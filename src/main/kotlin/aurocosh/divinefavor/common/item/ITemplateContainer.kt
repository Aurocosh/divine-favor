package aurocosh.divinefavor.common.item

import net.minecraft.item.ItemStack
import java.util.*

interface ITemplateContainer {
    fun getSelectedTemplateId(stack: ItemStack): UUID?
    fun getTemplatesIds(stack: ItemStack): List<UUID>
}