package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
import net.minecraft.init.MobEffects;

public class SpellTalismanVitalize extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanVitalize() {
        super("vitalize", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.removePotionEffect(MobEffects.WITHER);
        context.player.removePotionEffect(MobEffects.POISON);
    }
}
