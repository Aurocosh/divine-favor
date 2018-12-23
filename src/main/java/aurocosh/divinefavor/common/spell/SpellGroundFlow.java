package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;

public class SpellGroundFlow extends ModSpell {
    public SpellGroundFlow() {
        super("ground_flow");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.ground_flow));
    }
}
