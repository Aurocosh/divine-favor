package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.common.item.spell_talismans.sense.SenseBlockPredicate
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

class MessageParticlesHighlightInSphere : MessageParticlesHighlight {
    var radius: Int = 0

    constructor()

    constructor(particles: Int, dimensionId: Int, position: BlockPos, minShift: Float, maxShift: Float, color3f: Color3f, senseBlockPredicate: SenseBlockPredicate, blockName: String, radius: Int) : super(particles, dimensionId, position, minShift, maxShift, color3f, senseBlockPredicate, blockName) {
        this.radius = radius
    }

    @SideOnly(Side.CLIENT)
    override fun getHighlightPositions(player: EntityPlayer, predicate: (BlockPos) -> Boolean): Sequence<BlockPos> {
        return UtilCoordinates.getBlocksInSphere(position, radius).filter(predicate)
    }
}
