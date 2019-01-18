package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import net.minecraft.init.MobEffects;

public class SpellVitalize extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        context.player.removePotionEffect(MobEffects.WITHER);
        context.player.removePotionEffect(MobEffects.POISON);
    }
}
