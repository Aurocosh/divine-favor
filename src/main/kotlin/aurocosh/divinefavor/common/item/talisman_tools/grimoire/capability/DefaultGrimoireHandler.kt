package aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talisman_tools.DefaultStackContainerHandler
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import net.minecraft.item.ItemStack

// The default implementation of the capability. Holds all the logic.
class DefaultGrimoireHandler : DefaultStackContainerHandler(ItemGrimoire.SLOT_COUNT), IGrimoireHandler {
    override fun isItemValid(stack: ItemStack): Boolean {
        return stack.item is ItemSpellTalisman
    }
}
