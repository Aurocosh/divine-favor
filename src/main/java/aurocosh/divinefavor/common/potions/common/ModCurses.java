package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.potions.PotionFieryMark;
import aurocosh.divinefavor.common.potions.potions.PotionRoots;
import aurocosh.divinefavor.common.potions.potions.PotionSkybound;
import aurocosh.divinefavor.common.registry.ModRegistries;

public class ModCurses {
    public static ModPotion roots;
    public static ModPotion skybound;
    public static ModPotionTrigger fiery_mark;

    public static void preInit() {
        fiery_mark = ModRegistries.curses.register(new PotionFieryMark());
        roots = ModRegistries.curses.register(new PotionRoots());
        skybound = ModRegistries.curses.register(new PotionSkybound());

        for (ModPotion curse : ModRegistries.curses.getValues())
            curse.setIsCurse(true);
    }
}
