package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemLavawalkingTalisman extends ItemTalisman {
    public ItemLavawalkingTalisman() {
        super(LibItemNames.LAVAWALKING_TALISMAN, LibSpellRequirementNames.FREE,true,true);
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