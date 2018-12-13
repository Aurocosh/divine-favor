package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellWaterwalking extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellWaterwalking() {
        super("waterwalking");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect potioneffect = new ModEffect(ModPotions.water_walk, NORMAL);
        context.player.addPotionEffect(potioneffect);
        return true;
    }
}
