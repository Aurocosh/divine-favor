package aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability

import aurocosh.divinefavor.common.item.talisman_tools.ITalismanContainer
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman

interface ISpellBowHandler : ITalismanContainer {
    fun getSelectedTalisman(): ItemArrowTalisman?
}
