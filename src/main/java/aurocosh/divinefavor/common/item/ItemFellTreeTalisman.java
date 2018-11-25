package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.lib.LibItemNames;
import aurocosh.divinefavor.common.spell.base.ModSpells;

public class ItemFellTreeTalisman extends ItemTalisman {
    public ItemFellTreeTalisman() {
        super(LibItemNames.FELL_TREE_TALISMAN, LibSpellRequirementNames.FELL_TREE,true,false);
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