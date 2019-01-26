package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;

public class SpellTalismanTarget extends ItemSpellTalisman {
    public SpellTalismanTarget() {
        super("targer", 10, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        
    }
}
