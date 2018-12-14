package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;

public class SpellConsumingFury extends ModSpell {
    private final int FURY_DURATION = UtilTick.minutesToTicks(1);

    public SpellConsumingFury() {
        super("consuming_fury");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.consuming_fury, FURY_DURATION));
        return true;
    }
}
