package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;

public class SpellIgnition extends ModSpell {
    public SpellIgnition() {
        super("IGNITION");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        return UtilBlock.ignite(context.world, context.pos, context.facing);
    }
}
