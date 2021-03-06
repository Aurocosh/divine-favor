package aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability

import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talisman_tools.DefaultStackContainerHandler
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import net.minecraft.item.ItemStack

// The default implementation of the capability. Holds all the logic.
class DefaultSpellBladeHandler : DefaultStackContainerHandler(ItemSpellBlade.SLOT_COUNT), ISpellBladeHandler {
    override fun isItemValid(stack: ItemStack): Boolean {
        val item = stack.item
        return item is ItemSpellTalisman || item is ItemBladeTalisman
    }
}
