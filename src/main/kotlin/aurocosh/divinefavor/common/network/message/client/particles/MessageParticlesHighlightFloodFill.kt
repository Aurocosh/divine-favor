package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseBlockPredicate
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

class MessageParticlesHighlightFloodFill : MessageParticlesHighlight {
    var floodLimit: Int = 0
    var searchLimit: Int = 0
    var facing: EnumFacing = EnumFacing.UP

    constructor()

    constructor(particles: Int, dimensionId: Int, position: BlockPos, minShift: Float, maxShift: Float, color3f: Color3f, senseBlockPredicate: SenseBlockPredicate, blockName: String, floodLimit: Int, searchLimit: Int, facing: EnumFacing) : super(particles, dimensionId, position, minShift, maxShift, color3f, senseBlockPredicate, blockName) {
        this.floodLimit = floodLimit
        this.searchLimit = searchLimit
        this.facing = facing
    }

    @SideOnly(Side.CLIENT)
    override fun getHighlightPositions(player: EntityPlayer, predicate: (BlockPos) -> Boolean): Sequence<BlockPos> {
        val blockPos = UtilCoordinates.findPosition(this.position, floodLimit, player.world::isAirBlock) { pos -> pos.offset(facing) }
                ?: return emptySequence()
        return UtilCoordinates.floodFill(listOf(blockPos), BlockPosConstants.DIRECT_NEIGHBOURS, predicate, floodLimit).S
    }
}
