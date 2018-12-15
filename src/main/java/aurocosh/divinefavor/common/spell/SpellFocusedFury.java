package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;

public class SpellFocusedFury extends ModSpell {
    private final int FURY_DURATION = UtilTick.minutesToTicks(1);

    public SpellFocusedFury() {
        super("focused_fury");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.focused_fury, FURY_DURATION));
    }
}
