package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.effect.base.ModEffect;
import aurocosh.divinefavor.common.effect.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellWaterwalking extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellWaterwalking() {
        super(SpellType.WATERWALKING, true);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect potioneffect = new ModEffect(ModPotions.water_walk, NORMAL);
        context.player.addPotionEffect(potioneffect);
        return true;
    }
}
