package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.blends.PotionBurntSmell;

public class ModBlendEffects {
    public static ModPotion burnt_smell;

    public static void preInit() {
        burnt_smell = new PotionBurntSmell();
    }
}
