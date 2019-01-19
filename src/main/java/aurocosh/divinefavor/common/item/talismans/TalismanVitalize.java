package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import net.minecraft.init.MobEffects;

public class TalismanVitalize extends ItemTalisman {
    private static final int USES = 10;

    public TalismanVitalize() {
        super("vitalize", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.removePotionEffect(MobEffects.WITHER);
        context.player.removePotionEffect(MobEffects.POISON);
    }
}
