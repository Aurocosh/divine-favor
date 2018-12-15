package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.base.effect.PotionEffectCurse;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.init.MobEffects;

public class SpellWildSprint extends ModSpell {
    private final int SLOWNESS_DURATION = UtilTick.secondsToTicks(10);

    public SpellWildSprint() {
        super("wild_sprint");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        context.player.addPotionEffect(new ModEffectTrigger(ModPotions.wild_charge,SLOWNESS_DURATION));
//        context.player.addPotionEffect(new PotionEffectCurse(MobEffects.SLOWNESS,SLOWNESS_DURATION,SLOWNESS_LEVEL));
        return true;
    }
}
