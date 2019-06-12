package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.entries.arrow_talismans.ExplosiveArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanExplosive(name: String, spirit: ModSpirit, color: Color, options: EnumSet<ArrowOptions>, arrowType: ArrowType, config: ExplosiveArrow) : ItemArrowTalisman(name, spirit, config.favorCost, color, config.damage.toDouble(), options, arrowType) {
    private val explosionPower: Int = config.explosionPower
    private val damageTerrain: Boolean = config.damageTerrain
    private val causeFire: Boolean = config.causeFire

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        val damageTerrain = this.damageTerrain && !spellArrow.isInWater
        val arrowPosition = spellArrow.position
        spellArrow.world.newExplosion(spellArrow, arrowPosition.x.toDouble(), arrowPosition.y.toDouble(), arrowPosition.z.toDouble(), explosionPower.toFloat(), causeFire, damageTerrain)
        return true
    }
}
