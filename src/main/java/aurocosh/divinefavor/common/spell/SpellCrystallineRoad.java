package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class SpellCrystallineRoad extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellCrystallineRoad() {
        super("crystalline_road");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect potioneffect = new ModEffect(ModPotions.crystalline_road, NORMAL);
        context.player.addPotionEffect(potioneffect);
        return true;
    }
}
