package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemLavawalkingTalisman extends ItemTalisman {
    public ItemLavawalkingTalisman() {
        super(LibItemNames.LAVAWALKING_TALISMAN,true,true);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.lavawalking.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.lavawalking.cast(context);
    }
}