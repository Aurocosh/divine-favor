package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blessings.PotionChillingPresence;
import aurocosh.divinefavor.common.potions.blessings.PotionScorchingPresence;
import aurocosh.divinefavor.common.potions.blessings.PotionToweringPresence;

public class ModBlessings {
    public static ModPotion chilling_presence;
    public static ModPotion scorching_presence;
    public static ModPotion towering_presence;

    public static void preInit() {
        chilling_presence = new PotionChillingPresence();
        scorching_presence = new PotionScorchingPresence();
        towering_presence = new PotionToweringPresence();
    }
}
