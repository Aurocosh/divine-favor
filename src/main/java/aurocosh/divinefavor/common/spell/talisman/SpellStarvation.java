package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.potion.PotionEffect;

public class SpellStarvation extends ModSpell {
    public static int DURATION = UtilTick.minutesToTicks(2.5f);

    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new PotionEffect(ModPotions.starvation,DURATION));
    }
}
