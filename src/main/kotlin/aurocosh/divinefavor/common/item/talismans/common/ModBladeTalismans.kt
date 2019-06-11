package aurocosh.divinefavor.common.item.talismans.common

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.*
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.spirit.base.ModSpirit

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

    // New talisman vars
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
        butchering_strike = BladeTalismanButcheringStrike("butchering_strike", ModSpirits.squarefury, ConfigBlade.butcheringStrike.favorCost)
        confusion = BladeTalismanConfusion("confusion", ModSpirits.squarefury, ConfigBlade.confusion.favorCost)
        corrosion = BladeTalismanCorrosion("corrosion", ModSpirits.squarefury, ConfigBlade.corrosion.favorCost)
        gamble = BladeTalismanGamble("gamble", ModSpirits.squarefury, ConfigBlade.gamble.favorCost)
        hand_swap = BladeTalismanHandSwap("hand_swap", ModSpirits.squarefury, ConfigBlade.handSwap.favorCost)
        heavy_blade = BladeTalismanHeavy("heavy_blade", ModSpirits.squarefury, ConfigBlade.heavyBlade.favorCost)
        holy_blade = BladeTalismanHoly("holy_blade", ModSpirits.squarefury, ConfigBlade.holyBlade.favorCost)
        hungry_blade = BladeTalismanHungry("hungry_blade", ModSpirits.squarefury, ConfigBlade.hungryBlade.favorCost)
        poison_coating = BladeTalismanPoisonCoating("poison_coating", ModSpirits.squarefury, ConfigBlade.poisonCoating.favorCost)
        vengeful_blade = BladeTalismanVengeful("vengeful_blade", ModSpirits.squarefury, ConfigBlade.vengefulBlade.favorCost)

        // timber;
        wither_coating = BladeTalismanWitherCoating("wither_coating", ModSpirits.timber, ConfigBlade.witherCoating.favorCost)

        // New talismans
        lucky_strike = BladeTalismanLuckyStrike("lucky_strike", ModSpirits.squarefury, ConfigBlade.luckyStrike.favorCost)
        memory_blade = BladeTalismanMemoryBlade("memory_blade", ModSpirits.squarefury, ConfigBlade.memoryBlade.favorCost)
    }
}
