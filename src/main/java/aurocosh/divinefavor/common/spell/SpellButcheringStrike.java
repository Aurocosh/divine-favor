package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class SpellButcheringStrike extends ModSpell {
    private final int USES = 4;

    public SpellButcheringStrike() {
        super("butchering_strike");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        context.player.addPotionEffect(new ModEffectCharge(ModPotions.butchering_strike, USES));
        return true;
    }
}
