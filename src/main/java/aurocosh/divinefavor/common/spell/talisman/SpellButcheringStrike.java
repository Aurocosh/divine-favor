package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;

public class SpellButcheringStrike extends ModSpell {
    private final int USES = 4;

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectCharge(ModPotions.butchering_strike, USES));
    }
}
