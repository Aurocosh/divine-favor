package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

data class TalismanStackWrapper<T : ItemTalisman>(val stack: ItemStack, val talisman: T)

object TalismanAdapter {
    fun selectSlot(playerSlot: Int, talismanSlot: Int) {
        MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send()
    }

    inline fun <reified T : ItemTalisman> getTalisman(stack: ItemStack): TalismanStackWrapper<T>? {
        val item = stack.item
        if(item !is ITalismanStackContainer)
            return null
        val talismanStack = item.getTalismanStack(stack)
        if(talismanStack.isEmpty)
            return null
        val talisman = talismanStack.item as? T ?: return null
        return TalismanStackWrapper(talismanStack, talisman)
    }
}
