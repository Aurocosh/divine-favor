package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;

public class TalismanIgnition extends ItemTalisman {
    public TalismanIgnition(String name, int startingSpellUses, boolean castOnRightClick) {
        super(name, startingSpellUses, true, castOnRightClick);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        UtilBlock.ignite(context.world, context.pos, context.facing);
    }
}
