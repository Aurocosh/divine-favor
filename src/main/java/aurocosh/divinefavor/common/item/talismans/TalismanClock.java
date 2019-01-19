package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import net.minecraft.util.text.TextComponentString;

public class TalismanClock extends ItemTalisman {
    private static final int USES = 10;

    public TalismanClock() {
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
