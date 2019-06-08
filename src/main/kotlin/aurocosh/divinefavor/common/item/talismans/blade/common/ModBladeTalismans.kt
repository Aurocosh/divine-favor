package aurocosh.divinefavor.common.item.talismans.blade.common

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.config.entries.talismans.blade.RainSword
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.item.talismans.blade.BladeTalismanRainSword
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.ModSpirits

object ModBladeTalismans {
    lateinit var rain_sword: ItemBladeTalisman

    fun preInit() {
        // arbow;

        // blizrabi;
        rain_sword = BladeTalismanRainSword("rain_sword", ModSpirits.blizrabi, ConfigBlade.rainSword.favorCost)

        // endererer;

        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // timber;

    }
}
