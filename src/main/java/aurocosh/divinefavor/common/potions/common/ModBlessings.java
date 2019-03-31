package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.presences.*;

public class ModBlessings {
    public static ModPotion chilling_presence;
    public static ModPotion energetic_presence;
    public static ModPotion furious_presence;
    public static ModPotion industrious_presence;
    public static ModPotion manipulative_presence;
    public static ModPotion predatory_presence;
    public static ModPotion scorching_presence;
    public static ModPotion towering_presence;
    public static ModPotion warping_presence;

    public static void preInit() {
        chilling_presence = new PotionChillingPresence();
        energetic_presence = new PotionEnergeticPresence();
        furious_presence = new PotionFuriousPresence();
        industrious_presence = new PotionIndustriousPresence();
        manipulative_presence = new PotionManipulativePresence();
        predatory_presence = new PotionPredatoryPresence();
        scorching_presence = new PotionScorchingPresence();
        towering_presence = new PotionToweringPresence();
        warping_presence = new PotionWarpingPresence();
    }
}
