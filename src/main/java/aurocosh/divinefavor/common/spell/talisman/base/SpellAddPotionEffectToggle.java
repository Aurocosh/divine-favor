package aurocosh.divinefavor.common.spell.talisman.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;

public class SpellAddPotionEffectToggle extends ModSpell {
    private final ModPotionToggle potion;

    public SpellAddPotionEffectToggle(ModPotionToggle potion) {
        this.potion = potion;
    }

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectToggle(potion));
    }

    @Override
    public boolean isConsumeCharge(SpellContext context) {
        return !context.player.isPotionActive(potion);
    }
}
