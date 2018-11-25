package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemStoneballThrowTalisman extends ItemTalisman {
    public ItemStoneballThrowTalisman() {
        super(LibItemNames.STONEBALL_THROW_TALISMAN, LibSpellRequirementNames.STONEBALL_THROW,false,true);

        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return false;
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.stoneball_throw.cast(context);
    }
}