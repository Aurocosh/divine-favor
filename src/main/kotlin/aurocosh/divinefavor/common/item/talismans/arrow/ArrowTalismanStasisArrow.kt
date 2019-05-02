package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.getMotionVector
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*

class ArrowTalismanStasisArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun init(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        spellArrow.setDespawnDelay(ConfigArrow.stasisArrow.despawnDelay)
    }

    @SideOnly(Side.CLIENT)
    override fun spawnParticles(spellArrow: EntitySpellArrow) {
        for (i in 0..9) {
            val pointOnSphereSurface = UtilRandom.nextDirection().scale(ConfigArrow.stasisArrow.radius.toDouble())
            val pointInWorld = pointOnSphereSurface.add(spellArrow.positionVector)
            ModParticles.normal.createParticle(pointInWorld) { ImmobileParticle(spellArrow.world, pointInWorld, spellArrow.color, UtilRandom.nextInt(40, 50)) }
        }
    }

    override fun onCollideWithPlayer(spellArrow: EntitySpellArrow, player: EntityPlayer): Boolean {
        return false
    }

    override fun onUpdate(spellArrow: EntitySpellArrow) {
        if (!spellArrow.isInGround)
            return
        val entities = spellArrow.world.getEntitiesWithinAABB(Entity::class.java, AxisAlignedBB(spellArrow.position).grow(ConfigArrow.stasisArrow.radius.toDouble()))
        val affectedMobs = entities.S.filter { entity -> entity !== spellArrow && entity !is EntityPlayer && (entity is EntityLivingBase || entity is EntityArrow) && entity.getDistanceSq(spellArrow) <= RADIUS_SQ }
        for (affectedMob in affectedMobs)
            UtilEntity.setVelocity(affectedMob, affectedMob.getMotionVector(), 0.01f)
    }

    companion object {
        private val RADIUS_SQ = ConfigArrow.stasisArrow.radius * ConfigArrow.stasisArrow.radius
    }
}
