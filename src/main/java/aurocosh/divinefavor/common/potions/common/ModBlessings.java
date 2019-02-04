package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blessings.PotionScorchingPresence;

public class ModBlessings {
    public static ModPotion scorching_presence;

    public static void preInit() {
        scorching_presence = new PotionScorchingPresence();
    }
}
