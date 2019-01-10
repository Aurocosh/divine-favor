package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellWoodenPunch extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        PotionEffect effect = new ModEffectToggle(ModPotions.wooden_punch);
        context.player.addPotionEffect(effect);
    }
}
