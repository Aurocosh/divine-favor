package aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability;

import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;

public interface ISpellBowHandler extends ITalismanContainer {
    ItemArrowTalisman getSelectedTalisman();
}
