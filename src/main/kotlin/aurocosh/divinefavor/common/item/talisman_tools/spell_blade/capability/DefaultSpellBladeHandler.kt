package aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability

import aurocosh.divinefavor.common.item.talisman_tools.DefaultTalismanHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import net.minecraft.item.ItemStack

// The default implementation of the capability. Holds all the logic.
class DefaultSpellBladeHandler : DefaultTalismanHandler(ItemSpellBlade.SLOT_COUNT), ISpellBladeHandler {
    override fun isItemValid(stack: ItemStack): Boolean {
        return stack.item is ItemSpellTalisman
    }
}
