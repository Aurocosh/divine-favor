package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemArrowThrowTalisman extends ItemTalisman {
    public ItemArrowThrowTalisman() {
        super(LibItemNames.ARROW_THROW_TALISMAN, LibSpellRequirementNames.FREE,false,true);

        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return false;
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.arrow_throw.cast(context);
    }
}