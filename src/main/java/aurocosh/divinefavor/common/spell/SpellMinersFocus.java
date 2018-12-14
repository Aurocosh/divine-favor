package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class SpellMinersFocus extends ModSpell {
    private final int HASTE_DURATION = (int) (60 * 20 * 0.1);
    private final int HASTE_LEVEL = 4;

    public SpellMinersFocus() {
        super("miners_focus");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.miners_focus,HASTE_DURATION));
        context.player.addPotionEffect(new PotionEffect(MobEffects.HASTE,HASTE_DURATION,HASTE_LEVEL));
        return true;
    }
}
