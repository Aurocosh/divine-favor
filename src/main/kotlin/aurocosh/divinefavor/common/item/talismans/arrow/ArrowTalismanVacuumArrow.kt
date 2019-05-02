package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.MobileParticle
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*

class ArrowTalismanVacuumArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun init(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        spellArrow.setDespawnDelay(ConfigArrow.vacuumArrow.despawnDelay)
    }

    @SideOnly(Side.CLIENT)
    override fun spawnParticles(spellArrow: EntitySpellArrow) {
        for (i in 0..2) {
            val distance = UtilRandom.nextFloat(4f, 9f).toDouble()
            val direction = UtilRandom.nextDirection()
            val pointOnSphere = direction.scale(distance)
            val pointInWorld = pointOnSphere.add(spellArrow.positionVector)
            val speed = direction.scale(-0.3)
            ModParticles.normal.createParticle(pointInWorld) { MobileParticle(spellArrow.world, pointInWorld, speed, spellArrow.color) }
        }
    }

    override fun onUpdate(spellArrow: EntitySpellArrow) {
        if (spellArrow.world.isRemote)
            return
        if (!spellArrow.isInGround)
            return

        val livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB(spellArrow.position).grow(ConfigArrow.vacuumArrow.radius.toDouble()))
        val affectedMobs = livingBases.S.filter { livingBase -> livingBase !is EntityPlayer && livingBase.getDistanceSq(spellArrow) <= RADIUS_SQ }
        for (affectedMob in affectedMobs) {
            val direction = affectedMob.positionVector.subtract(spellArrow.positionVector)
            UtilEntity.addVelocity(affectedMob, direction, ConfigArrow.vacuumArrow.attractionPower)
        }
    }

    companion object {
        private val RADIUS_SQ = ConfigArrow.vacuumArrow.radius * ConfigArrow.vacuumArrow.radius
    }
}
