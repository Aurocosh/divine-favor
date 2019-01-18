package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;

public class SpellIgnition extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        UtilBlock.ignite(context.world, context.pos, context.facing);
    }
}
