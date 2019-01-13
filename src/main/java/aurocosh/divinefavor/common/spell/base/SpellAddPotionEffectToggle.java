package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.potion.PotionEffect;

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
