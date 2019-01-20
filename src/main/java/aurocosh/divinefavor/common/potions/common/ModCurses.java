package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.curses.*;
import aurocosh.divinefavor.common.registry.ModRegistries;

public class ModCurses {
    public static ModPotion armor_corrosion;
    public static ModPotion fiery_mark;
    public static ModPotion roots;
    public static ModPotion skyfall;
    public static ModPotion wind_leash;
    public static ModPotion limp_leg;

    public static void preInit() {
        armor_corrosion = ModRegistries.curses.register(new PotionArmorCorrosion());
        fiery_mark = ModRegistries.curses.register(new PotionFieryMark());
        limp_leg = ModRegistries.curses.register(new PotionLimpLeg());
        roots = ModRegistries.curses.register(new PotionRoots());
        skyfall = ModRegistries.curses.register(new PotionSkyfall());
        wind_leash = ModRegistries.curses.register(new PotionWindLeash());

        for (ModPotion curse : ModRegistries.curses.getValues())
            curse.setIsCurse(true);
    }
}
