package aurocosh.divinefavor.common.spirit

import aurocosh.divinefavor.common.config.common.ConfigSpirits
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.spirit.punishment.*

object ModSpirits {
    lateinit var arbow: ModSpirit
    lateinit var blizrabi: ModSpirit
    lateinit var endererer: ModSpirit
    lateinit var loon: ModSpirit
    lateinit var materia: ModSpirit
    lateinit var neblaze: ModSpirit
    lateinit var redwind: ModSpirit
    lateinit var romol: ModSpirit
    lateinit var squarefury: ModSpirit
    lateinit var timber: ModSpirit

    fun preInit() {
        arbow = ModSpirit("arbow", ArbowPunishment(), ConfigSpirits.arbow)
        blizrabi = ModSpirit("blizrabi", BlizrabiPunishment(), ConfigSpirits.blizrabi)
        endererer = ModSpirit("endererer", EnderererPunishment(), ConfigSpirits.endererer)
        loon = ModSpirit("loon", LoonPunishment(), ConfigSpirits.loon)
        materia = ModSpirit("materia", LoonPunishment(), ConfigSpirits.materia) // TODO replace punishment
        neblaze = ModSpirit("neblaze", NeblazePunishment(), ConfigSpirits.neblaze)
        redwind = ModSpirit("redwind", RedwindPunishment(), ConfigSpirits.redwind)
        romol = ModSpirit("romol", RomolPunishment(), ConfigSpirits.romol)
        squarefury = ModSpirit("squarefury", SquareFuryPunishment(), ConfigSpirits.squarefury)
        timber = ModSpirit("timber", TimberPunishment(), ConfigSpirits.timber)
    }
}