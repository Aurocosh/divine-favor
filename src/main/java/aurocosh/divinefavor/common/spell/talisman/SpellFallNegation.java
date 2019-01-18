package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellFallNegation extends ModSpell {
    private final int USES = 10;

    @Override
    protected void performActionServer(SpellContext context) {
        PotionEffect effect = new ModEffectCharge(ModPotions.fall_negation, USES);
        context.player.addPotionEffect(effect);
    }
}
