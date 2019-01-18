package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;

public class SpellMistBlade extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffect(ModPotions.mist_blade, NORMAL));
    }
}
