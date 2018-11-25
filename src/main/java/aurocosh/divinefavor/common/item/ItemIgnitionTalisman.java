package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemIgnitionTalisman extends ItemTalisman {
    public ItemIgnitionTalisman() {
        super(LibItemNames.IGNITION_TALISMAN, LibSpellRequirementNames.FREE,true,false);

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