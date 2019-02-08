package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blends.*;

public class ModBlendEffects {
    public static ModPotion arboreal_aura;
    public static ModPotion calling_aura;
    public static ModPotion charred_aura;
    public static ModPotion distorted_aura;
    public static ModPotion energetic_aura;
    public static ModPotion frosty_aura;
    public static ModPotion hunters_aura;
    public static ModPotion mineral_aura;
    public static ModPotion visceral_aura;

    public static void preInit() {
        arboreal_aura = new PotionArborealAura();
        calling_aura = new PotionCallingAura();
        charred_aura = new PotionCharredAura();
        distorted_aura = new PotionDistortedAura();
        energetic_aura = new PotionEnergeticAura();
        frosty_aura = new PotionFrostyAura();
        hunters_aura = new PotionHuntersAura();
        mineral_aura = new PotionMineralAura();
        visceral_aura = new PotionVisceralAura();
    }
}
