package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.talisman.ITalismanContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.SpellBladeDataHandler.CAPABILITY_SPELL_BLADE
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW
import aurocosh.divinefavor.common.lib.extensions.capNull
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot
import aurocosh.divinefavor.common.util.HeldStack
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

data class TalismanStackWrapper<T : ItemTalisman>(val stack: ItemStack, val talisman: T)

object TalismanAdapter {
    fun isItemValid(item: Item): Boolean {
        return item is ItemGrimoire || item is ItemSpellBow || item is ItemSpellBlade
    }

    fun getTalismanContainer(stack: ItemStack): ITalismanTool? {
        return stack.capNull(CAPABILITY_GRIMOIRE)
                ?: stack.capNull(CAPABILITY_SPELL_BOW)
                ?: stack.capNull(CAPABILITY_SPELL_BLADE)
    }

    fun getTalismanStack(stack: ItemStack): ItemStack {
        val container = getTalismanContainer(stack) ?: return stack
        return container.getSelectedStack()
    }

    fun selectSlot(playerSlot: Int, talismanSlot: Int) {
        MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send()
    }

    inline fun <reified T : ItemTalisman> getTalisman(stack: ItemStack): TalismanStackWrapper<T>? {
        val item = stack.item
        if(item !is ITalismanContainer)
            return null
        val talismanStack = item.getTalismanStack(stack)
        if(talismanStack.isEmpty)
            return null
        val talisman = talismanStack.item as? T ?: return null
        return TalismanStackWrapper(talismanStack, talisman)
    }
}
