package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;

import java.util.EnumSet;

public class SpellTalismanIgnition extends ItemSpellTalisman {
    public SpellTalismanIgnition(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        UtilBlock.ignite(context.world, context.pos, context.facing);
    }
}
