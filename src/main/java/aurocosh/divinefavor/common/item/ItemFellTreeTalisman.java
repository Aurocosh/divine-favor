package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemFellTreeTalisman extends ItemTalisman {
    public ItemFellTreeTalisman() {
        super(LibItemNames.FELL_TREE_TALISMAN, LibSpellRequirementNames.FREE,true,false);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public boolean castUse(SpellContext context) {
        return ModSpells.fell_tree.cast(context);
    }

    @Override
    public boolean castRightClick(SpellContext context) {
        return false;
    }
}