package aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability

import aurocosh.divinefavor.common.item.talisman_tools.DefaultTalismanHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import net.minecraft.item.ItemStack

// The default implementation of the capability. Holds all the logic.
class DefaultSpellPickHandler : DefaultTalismanHandler(ItemSpellBlade.SLOT_COUNT), ISpellPickHandler {
    override fun isItemValid(stack: ItemStack): Boolean {
        val item = stack.item
        return item is ItemSpellTalisman || item is ItemToolTalisman
    }
}
