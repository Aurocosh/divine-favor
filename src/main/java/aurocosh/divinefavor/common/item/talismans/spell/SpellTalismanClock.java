package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.util.text.TextComponentString;

public class SpellTalismanClock extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanClock() {
        super("clock", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
    }

    @Override
    protected void performActionClient(TalismanContext context) {
        long time = context.world.getWorldTime();
        context.player.sendMessage(new TextComponentString("Time: " + time));
        context.player.sendMessage(new TextComponentString("Day time: " + time % 24000));
    }
}
