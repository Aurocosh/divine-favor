package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class TalismanMinersFocus extends ItemTalisman {
    private static final int HASTE_DURATION = (int) (60 * 20 * 0.1);
    private static final int HASTE_LEVEL = 4;
    private static final int USES = 10;

    public TalismanMinersFocus() {
        super("miners_focus", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.miners_focus,HASTE_DURATION));
        context.player.addPotionEffect(new PotionEffect(MobEffects.HASTE,HASTE_DURATION,HASTE_LEVEL));
    }
}
