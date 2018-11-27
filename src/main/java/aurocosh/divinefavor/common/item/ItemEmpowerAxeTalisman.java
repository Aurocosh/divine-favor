package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemEmpowerAxeTalisman extends ItemTalisman {
    public ItemEmpowerAxeTalisman() {
        super(LibItemNames.EMPOWER_AXE_TALISMAN, LibSpellRequirementNames.FREE,true,true);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.empower_axe.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return ModSpells.empower_axe.cast(context);
    }
}