package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemIgnitionTalisman extends ItemTalisman {
    public ItemIgnitionTalisman() {
        super(LibItemNames.IGNITION_TALISMAN, LibSpellRequirementNames.IGNITION,true,false);

        //new AssemblyScavengeRecipe();
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.ignition.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return false;
    }
}