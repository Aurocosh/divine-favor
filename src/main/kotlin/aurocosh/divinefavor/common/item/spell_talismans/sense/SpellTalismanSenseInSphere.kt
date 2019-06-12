package aurocosh.divinefavor.common.item.spell_talismans.sense

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.config.entries.spell_talismans.generic.SenseConfig
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightInSphere
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import java.util.*
import javax.vecmath.Color3f

abstract class SpellTalismanSenseInSphere(name: String, spirit: ModSpirit, options: EnumSet<SpellOptions>, protected val color3f: Color3f, senseConfig: SenseConfig, protected val predicate: SenseBlockPredicate) : ItemSpellTalisman(name, "sense/", spirit, senseConfig.favorCost, options) {
    protected val radius: Int = senseConfig.radius
    protected val minShift: Float = senseConfig.minShift
    protected val maxShift: Float = senseConfig.maxShift

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        highlightBlocks(radius, player, player.position, minShift, maxShift, color3f, "")
    }

    protected fun highlightBlocks(radius: Int, player: EntityPlayer, position: BlockPos, minShift: Float, maxShift: Float, color3f: Color3f, blockName: String) {
        val world = player.world
        MessageParticlesHighlightInSphere(5, world.provider.dimension, position, minShift, maxShift, color3f, predicate, blockName, radius)
                .sendToAllAround(world, position, ConfigGeneral.particleRadius)
    }
}
