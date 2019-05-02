package aurocosh.divinefavor.common.potions.common

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.curses.*
import aurocosh.divinefavor.common.potions.potions.PotionSoulTheft

object ModCurses {
    lateinit var armor_corrosion: ModPotion
    lateinit var crawling_mist: ModPotion
    lateinit var cripple: ModPotion
    lateinit var evil_eye: ModPotion
    lateinit var fiery_mark: ModPotion
    lateinit var fill_lungs: ModPotion
    lateinit var hollow_leg: ModPotion
    lateinit var limp_leg: ModPotion
    lateinit var petrification: ModPotion
    lateinit var red_fury: ModPotion
    lateinit var roots: ModPotion
    lateinit var skyfall: ModPotion
    lateinit var soul_theft: ModPotion
    lateinit var suffocating_fumes: ModPotion
    lateinit var wind_leash: ModPotion
    lateinit var yummy_smell: ModPotion

    fun preInit() {
        armor_corrosion = PotionArmorCorrosion()
        crawling_mist = PotionCrawlingMist()
        cripple = PotionCripple()
        evil_eye = PotionEvilEye()
        fiery_mark = PotionFieryMark()
        fill_lungs = PotionFillLungs()
        hollow_leg = PotionHollowLeg()
        limp_leg = PotionLimpLeg()
        petrification = PotionPetrification()
        red_fury = PotionRedFury()
        roots = PotionRoots()
        skyfall = PotionSkyfall()
        soul_theft = PotionSoulTheft()
        suffocating_fumes = PotionSuffocatingFumes()
        wind_leash = PotionWindLeash()
        yummy_smell = PotionYummySmell()
    }
}
