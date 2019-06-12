package aurocosh.divinefavor.common.item.spell_talismans.sense

import aurocosh.divinefavor.common.lib.extensions.selectRandom
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.OreHighlightParticle
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

@SideOnly(Side.CLIENT)
object BlockHighlighter {
    private val MAX_BLOCKS_HIGHLIGHTED = 500

    fun spawnParticles(color3f: Color3f, maxShift: Float, minShift: Float, particles: Int, world: World, particlePositions: List<Vec3d>) {
        var particlePosList = particlePositions
        if (particlePosList.size > MAX_BLOCKS_HIGHLIGHTED)
            particlePosList = particlePosList.selectRandom(MAX_BLOCKS_HIGHLIGHTED)

        for (pos in particlePosList) {
            for (i in 0 until particles) {
                val directionShift = UtilRandom.nextDirection()
                val shift = directionShift.scale(UtilRandom.nextFloat(minShift, maxShift).toDouble())
                val position = pos.add(shift)

                ModParticles.noDepth.createParticle { OreHighlightParticle(world, position, color3f) }
            }
        }
    }
}
