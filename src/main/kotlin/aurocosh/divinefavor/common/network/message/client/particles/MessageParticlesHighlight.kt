package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.spell_talismans.sense.BlockHighlighter
import aurocosh.divinefavor.common.item.spell_talismans.sense.SenseBlockPredicate
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isLava
import aurocosh.divinefavor.common.lib.extensions.isLiquid
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.lib.wrapper.ConvertingPredicate
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

abstract class MessageParticlesHighlight : DivineClientMessage {
    var particles: Int = 0
    var dimensionId: Int = 0
    var position: BlockPos = BlockPos.ORIGIN

    var minShift: Float = 0f
    var maxShift: Float = 0f
    var color3f: Color3f = Color3f(1f, 1f, 1f)

    var senseBlockPredicate: SenseBlockPredicate = SenseBlockPredicate.BLOCK
    var blockName: String = ""

    constructor()

    constructor(particles: Int, dimensionId: Int, position: BlockPos, minShift: Float, maxShift: Float, color3f: Color3f, senseBlockPredicate: SenseBlockPredicate, blockName: String) {
        this.particles = particles
        this.dimensionId = dimensionId
        this.position = position
        this.minShift = minShift
        this.maxShift = maxShift
        this.color3f = color3f
        this.senseBlockPredicate = senseBlockPredicate
        this.blockName = blockName
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val world = player.world
        if (dimensionId != world.provider.dimension)
            return

        val predicate = getPredicate(world)
        val vec3dList = getHighlightPositions(player, predicate).map { Vec3d(it) }.toList()
        BlockHighlighter.spawnParticles(color3f, maxShift, minShift, particles, world, vec3dList)
    }

    @SideOnly(Side.CLIENT)
    protected abstract fun getHighlightPositions(player: EntityPlayer, predicate: (BlockPos) -> Boolean): Sequence<BlockPos>

    @SideOnly(Side.CLIENT)
    private fun getPredicate(world: World): (BlockPos) -> Boolean {
        when (senseBlockPredicate) {
            SenseBlockPredicate.BLOCK -> {
                val block = Block.getBlockFromName(blockName)
                return if (block == null) { _ -> false } else { pos -> world.getBlock(pos) === block }
            }
            SenseBlockPredicate.WATER -> return ConvertingPredicate(world::getBlockState, IBlockState::isWater)::invoke
            SenseBlockPredicate.LAVA -> return ConvertingPredicate(world::getBlockState, IBlockState::isLava)::invoke
            SenseBlockPredicate.LIQUID -> return ConvertingPredicate(world::getBlockState, IBlockState::isLiquid)::invoke
            SenseBlockPredicate.ORE -> return ConvertingPredicate(world::getBlock, ConfigGeneral.ORE_BLOCKS::contains)::invoke
            SenseBlockPredicate.AIR -> return world::isAirBlock
        }
    }
}
