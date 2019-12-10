package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.*
import aurocosh.divinefavor.common.item.blade_talismans.base.BladeTalismanModPotion
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.ModSpirits

object ModBladeTalismans {
    lateinit var blade_of_snow: ItemBladeTalisman
    lateinit var butchering_strike: ItemBladeTalisman
    lateinit var confusion: ItemBladeTalisman
    lateinit var corrosion: ItemBladeTalisman
    lateinit var gamble: ItemBladeTalisman
    lateinit var hand_swap: ItemBladeTalisman
    lateinit var heavy_blade: ItemBladeTalisman
    lateinit var holy_blade: ItemBladeTalisman
    lateinit var hungry_blade: ItemBladeTalisman
    lateinit var inflame: ItemBladeTalisman
    lateinit var poison_coating: ItemBladeTalisman
    lateinit var rain_sword: ItemBladeTalisman
    lateinit var vengeful_blade: ItemBladeTalisman
    lateinit var wither_coating: ItemBladeTalisman

    // New fields
    lateinit var obliteration: ItemBladeTalisman
    lateinit var yummy_smell: ItemBladeTalisman
    lateinit var wind_leash: ItemBladeTalisman
    lateinit var suffocating_fumes: ItemBladeTalisman
    lateinit var skyfall: ItemBladeTalisman
    lateinit var fill_lungs: ItemBladeTalisman
    lateinit var fiery_mark: ItemBladeTalisman
    lateinit var crawling_mist: ItemBladeTalisman
    lateinit var lucky_strike: ItemBladeTalisman
    lateinit var memory_blade: ItemBladeTalisman

    fun preInit() {
        // arbow;

        // blizrabi;
        blade_of_snow = BladeTalismanSnow("blade_of_snow", ModSpirits.blizrabi, ConfigBlade.bladeOfSnow.favorCost)
        rain_sword = BladeTalismanRainSword("rain_sword", ModSpirits.blizrabi, ConfigBlade.rainSword.favorCost)

        // endererer;

        // loon;

        // neblaze;
        inflame = BladeTalismanInflame("inflame", ModSpirits.neblaze, ConfigBlade.inflame.favorCost)

        // redwind;

        // romol;

        // squarefury;
        gamble = BladeTalismanGamble("gamble", ModSpirits.squarefury, ConfigBlade.gamble.favorCost)
        hungry_blade = BladeTalismanHungry("hungry_blade", ModSpirits.squarefury, ConfigBlade.hungryBlade.favorCost)
        poison_coating = BladeTalismanPoisonCoating("poison_coating", ModSpirits.squarefury, ConfigBlade.poisonCoating.favorCost)
        vengeful_blade = BladeTalismanVengeful("vengeful_blade", ModSpirits.squarefury, ConfigBlade.vengefulBlade.favorCost)

        butchering_strike = BladeTalismanButcheringStrike("butchering_strike", ModSpirits.squarefury, ConfigBlade.butcheringStrike.favorCost)
        confusion = BladeTalismanConfusion("confusion", ModSpirits.squarefury, ConfigBlade.confusion.favorCost)
        corrosion = BladeTalismanCorrosion("corrosion", ModSpirits.squarefury, ConfigBlade.corrosion.favorCost)
        hand_swap = BladeTalismanHandSwap("hand_swap", ModSpirits.squarefury, ConfigBlade.handSwap.favorCost)
        heavy_blade = BladeTalismanHeavy("heavy_blade", ModSpirits.squarefury, ConfigBlade.heavyBlade.favorCost)
        holy_blade = BladeTalismanHoly("holy_blade", ModSpirits.squarefury, ConfigBlade.holyBlade.favorCost)
        obliteration = BladeTalismanObliteration("obliteration", ModSpirits.squarefury, ConfigBlade.obliteration.favorCost)

        // timber;
        wither_coating = BladeTalismanWitherCoating("wither_coating", ModSpirits.timber, ConfigBlade.witherCoating.favorCost)
        fill_lungs = BladeTalismanModPotion("fill_lungs", ModSpirits.timber, ConfigBlade.fillLungs.favorCost, ModCurses.fill_lungs, ConfigBlade.fillLungs.duration)
        suffocating_fumes = BladeTalismanModPotion("suffocating_fumes", ModSpirits.timber, ConfigBlade.suffocatingFumes.favorCost, ModCurses.suffocating_fumes, ConfigBlade.suffocatingFumes.duration)

        yummy_smell = BladeTalismanModPotion("yummy_smell", ModSpirits.timber, ConfigBlade.yummySmell.favorCost, ModCurses.yummy_smell, ConfigBlade.yummySmell.duration)
        wind_leash = BladeTalismanModPotion("wind_leash", ModSpirits.timber, ConfigBlade.windLeash.favorCost, ModCurses.wind_leash, ConfigBlade.windLeash.duration)
        skyfall = BladeTalismanModPotion("skyfall", ModSpirits.timber, ConfigBlade.skyfall.favorCost, ModCurses.skyfall, ConfigBlade.skyfall.duration)
        fiery_mark = BladeTalismanModPotion("fiery_mark", ModSpirits.neblaze, ConfigBlade.fieryMark.favorCost, ModCurses.fiery_mark, ConfigBlade.fieryMark.duration)
        crawling_mist = BladeTalismanModPotion("crawling_mist", ModSpirits.timber, ConfigBlade.crawlingMist.favorCost, ModCurses.crawling_mist, ConfigBlade.crawlingMist.duration)
        lucky_strike = BladeTalismanLuckyStrike("lucky_strike", ModSpirits.squarefury, ConfigBlade.luckyStrike.favorCost)
        memory_blade = BladeTalismanMemoryBlade("memory_blade", ModSpirits.squarefury, ConfigBlade.memoryBlade.favorCost)

        // New instances
    }
}
