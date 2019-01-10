package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellMistBlade extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffect(ModPotions.mist_blade, NORMAL));
    }
}
