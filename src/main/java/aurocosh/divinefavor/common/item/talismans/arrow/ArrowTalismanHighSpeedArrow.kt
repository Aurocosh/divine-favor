package aurocosh.divinefavor.common.item.talismans.arrow

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.projectile.EntityArrow
import java.awt.Color
import java.util.*

class ArrowTalismanHighSpeedArrow(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType, private val extraVelocity: Float) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun postInit(spellArrow: EntityArrow) {
        val direction = UtilEntity.getMotionVector(spellArrow)
        UtilEntity.addVelocity(spellArrow, direction, extraVelocity)
    }
}
