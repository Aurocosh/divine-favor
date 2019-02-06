package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blessings.*;

public class ModBlessings {
    public static ModPotion chilling_presence;
    public static ModPotion energetic_presence;
    public static ModPotion furious_presence;
    public static ModPotion scorching_presence;
    public static ModPotion towering_presence;

    public static void preInit() {
        chilling_presence = new PotionChillingPresence();
        energetic_presence = new PotionEnergeticPresence();
        furious_presence = new PotionFuriousPresence();
        scorching_presence = new PotionScorchingPresence();
        towering_presence = new PotionToweringPresence();
    }
}
