package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;

public class TalismanModPotionTrigger extends ItemTalisman {
    private final ModPotionTrigger potion;
    private int duration;

    public TalismanModPotionTrigger(String name, int startingSpellUses, ModPotionTrigger potion, int duration) {
        super(name, startingSpellUses, true, true);
        this.potion = potion;
        this.duration = duration;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(potion, duration));
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        return !context.player.isPotionActive(potion);
    }
}
