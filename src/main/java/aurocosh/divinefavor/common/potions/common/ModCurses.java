package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.curses.*;
import aurocosh.divinefavor.common.registry.ModRegistries;

public class ModCurses {
    public static ModPotion armor_corrosion;
    public static ModPotion fiery_mark;
    public static ModPotion roots;
    public static ModPotion skybound;
    public static ModPotion wind_leash;

    public static void preInit() {
        armor_corrosion = ModRegistries.curses.register(new PotionArmorCorrosion());
        fiery_mark = ModRegistries.curses.register(new PotionFieryMark());
        roots = ModRegistries.curses.register(new PotionRoots());
        skybound = ModRegistries.curses.register(new PotionSkybound());
        wind_leash = ModRegistries.curses.register(new PotionWindLeash());

        for (ModPotion curse : ModRegistries.curses.getValues())
            curse.setIsCurse(true);
    }
}
