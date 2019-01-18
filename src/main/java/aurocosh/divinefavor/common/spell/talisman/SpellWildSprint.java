package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;

public class SpellWildSprint extends ModSpell {
    private final int SLOWNESS_DURATION = UtilTick.secondsToTicks(10);

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.wild_charge,SLOWNESS_DURATION));
    }
}
