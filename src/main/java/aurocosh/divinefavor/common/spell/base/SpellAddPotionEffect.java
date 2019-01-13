package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;

public class SpellAddPotionEffect extends ModSpell {
    private final int duration;
    private final int amplifier;
    private final ModPotion potion;

    public SpellAddPotionEffect(ModPotion potion, int duration) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = 0;
    }

    public SpellAddPotionEffect(ModPotion potion, int duration, int amplifier) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffect(potion, duration, amplifier));
    }
}
