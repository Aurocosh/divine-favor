package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.config.entries.arrow_talismans.GambleArrow
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanGamble(name: String, spirit: ModSpirit, color: Color, options: EnumSet<ArrowOptions>, arrowType: ArrowType, val config: GambleArrow) : ItemArrowTalisman(name, spirit, config.favorCost, color, config.damage.toDouble(), options, arrowType) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (UtilRandom.rollDiceFloat(config.failProbability))
            shooter.attackEntityFrom(ModDamageSources.divineDamage, config.failDamage)
        return true
    }
}
