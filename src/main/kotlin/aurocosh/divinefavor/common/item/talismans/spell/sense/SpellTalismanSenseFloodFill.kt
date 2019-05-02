package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseConfig
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightFloodFill
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*
import javax.vecmath.Color3f

class SpellTalismanSenseFloodFill(name: String, spirit: ModSpirit, options: EnumSet<SpellOptions>, protected val color3f: Color3f, senseConfig: SenseConfig, protected val predicate: SenseBlockPredicate, protected val floodLimit: Int, protected val searchLimit: Int) : ItemSpellTalisman(name, "sense/", spirit, senseConfig.favorCost, options) {
    protected val minShift: Float = senseConfig.minShift
    protected val maxShift: Float = senseConfig.maxShift

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val world = player.world

        MessageParticlesHighlightFloodFill(5, world.provider.dimension, context.pos, minShift, maxShift, color3f, predicate, "", floodLimit, searchLimit, context
                .facing.opposite).sendToAllAround(world, player.position, ConfigGeneral.particleRadius)
    }
}
