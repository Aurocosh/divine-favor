package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemSmallFireballThrowTalisman extends ItemTalisman {
    public ItemSmallFireballThrowTalisman() {
        super(LibItemNames.SMALL_FIREBALL_THROW_TALISMAN,false,true);

        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return false;
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.small_fireball_throw.cast(context);
    }
}