package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.AxisAlignedBB
import java.awt.Color
import java.util.*

class ArrowTalismanMineArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun preInit(spellArrow: EntitySpellArrow, shooter: EntityLivingBase) {
        spellArrow.setDespawnDelay(ConfigArrow.mineArrow.despawnDelay)
    }

    override fun onCollideWithPlayer(spellArrow: EntitySpellArrow, player: EntityPlayer): Boolean {
        return false
    }

    override fun onUpdate(spellArrow: EntitySpellArrow) {
        if (spellArrow.world.isRemote)
            return
        if (!spellArrow.isInGround)
            return

        val livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB(spellArrow.position).grow(ConfigArrow.mineArrow.radius.toDouble()))
        if (livingBases.isEmpty())
            return

        val damageTerrain = ConfigArrow.mineArrow.damageTerrain && !spellArrow.isInWater
        val arrowPosition = spellArrow.position
        spellArrow.world.newExplosion(spellArrow, arrowPosition.x.toDouble(), arrowPosition.y.toDouble(), arrowPosition.z.toDouble(), ConfigArrow.mineArrow.explosionPower, ConfigArrow.mineArrow.causeFire, damageTerrain)
        spellArrow.setDead()
    }
}
