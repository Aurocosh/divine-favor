package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellCrystallineRoad extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    @Override
    protected void performActionServer(SpellContext context) {
        PotionEffect potioneffect = new ModEffect(ModPotions.crystalline_road, NORMAL);
        context.player.addPotionEffect(potioneffect);
    }
}
