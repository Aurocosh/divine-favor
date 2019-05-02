package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.entity.projectile.EntityClimbingArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import java.awt.Color
import java.util.*

open class ArrowTalismanClimbableArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType, protected val climbingSpeed: Float, protected val climbingDistance: Float, protected val despawnDelay: Int) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {
    protected val climbingDistanceSq: Float = climbingDistance * climbingDistance

    override fun getArrow(world: World, shooter: EntityLivingBase): EntitySpellArrow {
        val arrow = EntityClimbingArrow(world, shooter)
        arrow.setClimbingStats(climbingSpeed, climbingDistanceSq, despawnDelay)
        return arrow
    }

    override fun onCollideWithPlayer(spellArrow: EntitySpellArrow, player: EntityPlayer): Boolean {
        return false
    }
}
