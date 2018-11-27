package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemBonemealTalisman extends ItemTalisman {
    public ItemBonemealTalisman() {
        super(LibItemNames.BONEMEAL_TALISMAN, LibSpellRequirementNames.FREE,true,false);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.bonemeal.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return false;
    }
}