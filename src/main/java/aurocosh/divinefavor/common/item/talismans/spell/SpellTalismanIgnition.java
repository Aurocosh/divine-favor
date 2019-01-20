package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;

public class SpellTalismanIgnition extends ItemSpellTalisman {
    public SpellTalismanIgnition(String name, int startingSpellUses, boolean castOnRightClick) {
        super(name, startingSpellUses, true, castOnRightClick);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        UtilBlock.ignite(context.world, context.pos, context.facing);
    }
}
