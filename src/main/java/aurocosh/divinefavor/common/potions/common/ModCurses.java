package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.curses.PotionArmorCorrosion;
import aurocosh.divinefavor.common.potions.curses.PotionFieryMark;
import aurocosh.divinefavor.common.potions.curses.PotionRoots;
import aurocosh.divinefavor.common.potions.curses.PotionSkybound;
import aurocosh.divinefavor.common.registry.ModRegistries;

public class ModCurses {
    public static ModPotion roots;
    public static ModPotion skybound;
    public static ModPotion armor_corrosion;
    public static ModPotionTrigger fiery_mark;

    public static void preInit() {
        fiery_mark = ModRegistries.curses.register(new PotionFieryMark());
        roots = ModRegistries.curses.register(new PotionRoots());
        skybound = ModRegistries.curses.register(new PotionSkybound());
        armor_corrosion = ModRegistries.curses.register(new PotionArmorCorrosion());

        for (ModPotion curse : ModRegistries.curses.getValues())
            curse.setIsCurse(true);
    }
}
