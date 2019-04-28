package aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability

import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman

interface ISpellBowHandler : ITalismanContainer {
    fun getSelectedTalisman(): ItemArrowTalisman?
}
