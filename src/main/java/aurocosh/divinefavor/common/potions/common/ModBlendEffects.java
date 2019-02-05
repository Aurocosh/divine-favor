package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blends.PotionArborealAura;
import aurocosh.divinefavor.common.potions.blends.PotionCharredAura;

public class ModBlendEffects {
    public static ModPotion charred_aura;
    public static ModPotion arboreal_aura;

    public static void preInit() {
        charred_aura = new PotionCharredAura();
        arboreal_aura = new PotionArborealAura();
    }
}
