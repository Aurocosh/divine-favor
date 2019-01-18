package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;

public class SpellGroundFlow extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.ground_flow));
    }
}
