package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;

public class SpellWildSprint extends ModSpell {
    private final int SLOWNESS_DURATION = UtilTick.secondsToTicks(10);

    public SpellWildSprint() {
        super("wild_sprint");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.wild_charge,SLOWNESS_DURATION));
    }
}
