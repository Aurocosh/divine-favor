package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavors;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;

public class SpellTalismanTarget extends ItemSpellTalisman {
    public SpellTalismanTarget() {
        super("targer", ModFavors.allfire, 10, SpellOptions.ALL_CAST);
    }

    @Override
    protected void performActionServer(TalismanContext context) {

    }
}
