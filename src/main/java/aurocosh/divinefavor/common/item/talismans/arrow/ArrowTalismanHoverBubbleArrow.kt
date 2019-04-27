package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*

class ArrowTalismanHoverBubbleArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType, climbingSpeed: Float, climbingDistance: Float, despawnDelay: Int) : ArrowTalismanClimbableArrow(name, spirit, favorCost, color, arrowDamage, options, arrowType, climbingSpeed, climbingDistance, despawnDelay) {

    @SideOnly(Side.CLIENT)
    override fun spawnParticles(spellArrow: EntitySpellArrow) {
        for (i in 0..9) {
            val pointOnSphereSurface = UtilRandom.nextDirection().scale(climbingDistance.toDouble())
            val pointInWorld = pointOnSphereSurface.add(spellArrow.positionVector)
            ModParticles.normal.createParticle(pointInWorld) { ImmobileParticle(spellArrow.world, pointInWorld, spellArrow.color, UtilRandom.nextInt(40, 50)) }
        }
    }
}
