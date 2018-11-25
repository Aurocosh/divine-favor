package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemSnowballThrowTalisman extends ItemTalisman {
    public ItemSnowballThrowTalisman() {
        super(LibItemNames.SNOWBALL_THROW_TALISMAN, LibSpellRequirementNames.FREE,false,true);

        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return false;
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.snowball_throw.cast(context);
    }
}