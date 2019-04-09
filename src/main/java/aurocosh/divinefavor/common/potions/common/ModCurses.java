package aurocosh.divinefavor.common.potions.common;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.curses.*;
import aurocosh.divinefavor.common.potions.potions.PotionSoulTheft;

public class ModCurses {
    public static ModPotion armor_corrosion;
    public static ModPotion crawling_mist;
    public static ModPotion cripple;
    public static ModPotion evil_eye;
    public static ModPotion fiery_mark;
    public static ModPotion fill_lungs;
    public static ModPotion hollow_leg;
    public static ModPotion limp_leg;
    public static ModPotion petrification;
    public static ModPotion red_fury;
    public static ModPotion roots;
    public static ModPotion skyfall;
    public static ModPotion soul_theft;
    public static ModPotion suffocating_fumes;
    public static ModPotion wind_leash;
    public static ModPotion yummy_smell;

    public static void preInit() {
        armor_corrosion = new PotionArmorCorrosion();
        crawling_mist = new PotionCrawlingMist();
        cripple = new PotionCripple();
        evil_eye = new PotionEvilEye();
        fiery_mark = new PotionFieryMark();
        fill_lungs = new PotionFillLungs();
        hollow_leg = new PotionHollowLeg();
        limp_leg = new PotionLimpLeg();
        petrification = new PotionPetrification();
        red_fury = new PotionRedFury();
        roots = new PotionRoots();
        skyfall = new PotionSkyfall();
        soul_theft = new PotionSoulTheft();
        suffocating_fumes = new PotionSuffocatingFumes();
        wind_leash = new PotionWindLeash();
        yummy_smell = new PotionYummySmell();
    }
}
