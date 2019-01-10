package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellEmpowerPickaxe extends ModSpell {
    private final int SHORT = 1800;

    @Override
    protected void performActionServer(SpellContext context) {
        PotionEffect effect = new ModEffect(ModPotions.empower_pickaxe, SHORT);
        context.player.addPotionEffect(effect);
    }
}
