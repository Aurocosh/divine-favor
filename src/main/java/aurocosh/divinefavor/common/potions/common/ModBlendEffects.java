package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blends.PotionArborealAura;
import aurocosh.divinefavor.common.potions.blends.PotionCharredAura;
import aurocosh.divinefavor.common.potions.blends.PotionEnergeticAura;
import aurocosh.divinefavor.common.potions.blends.PotionFrostyAura;

public class ModBlendEffects {
    public static ModPotion arboreal_aura;
    public static ModPotion charred_aura;
    public static ModPotion energetic_aura;
    public static ModPotion frosty_aura;

    public static void preInit() {
        arboreal_aura = new PotionArborealAura();
        charred_aura = new PotionCharredAura();
        energetic_aura = new PotionEnergeticAura();
        frosty_aura = new PotionFrostyAura();
    }
}
