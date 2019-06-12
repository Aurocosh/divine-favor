package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*

class ArrowTalismanTracerArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    @SideOnly(Side.CLIENT)
    override fun spawnParticles(spellArrow: EntitySpellArrow) {
        if (spellArrow.isInGround)
            return

        for (i in 0 until ConfigArrow.tracerArrow.particleDensity) {
            val position = spellArrow.getPositionEyes(UtilRandom.random.nextFloat())
            ModParticles.normal.createParticle { ImmobileParticle(spellArrow.world, position, spellArrow.color, ConfigArrow.tracerArrow.despawnInterval.random()) }
        }
    }
}
