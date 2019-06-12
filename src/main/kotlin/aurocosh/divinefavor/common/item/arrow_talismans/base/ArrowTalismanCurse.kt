package aurocosh.divinefavor.common.item.arrow_talismans.base

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCurses
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color

class ArrowTalismanCurse(name: String, spirit: ModSpirit, favorCost: Int, color: Color, private val potion: ModPotion, private val duration: Int) : ItemArrowTalisman(name, spirit, favorCost, color, 0.0, ArrowOptions.REQUIRES_TARGET, ArrowType.CURSED_ARROW) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target != null)
            UtilCurses.applyCurse(target, shooter, ModEffect(potion, duration))
        return true
    }
}