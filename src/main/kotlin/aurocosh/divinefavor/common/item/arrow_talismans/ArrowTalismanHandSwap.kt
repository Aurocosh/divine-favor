package aurocosh.divinefavor.common.item.arrow_talismans

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.awt.Color
import java.util.*

class ArrowTalismanHandSwap(name: String, spirit: ModSpirit, favorCost: Int, color: Color, arrowDamage: Double, options: EnumSet<ArrowOptions>, arrowType: ArrowType) : ItemArrowTalisman(name, spirit, favorCost, color, arrowDamage, options, arrowType) {

    override fun performActionServer(target: EntityLivingBase?, shooter: EntityLivingBase, spellArrow: EntitySpellArrow, blockPos: BlockPos?, sideHit: EnumFacing?): Boolean {
        if (target is EntityPlayer)
            UtilPlayer.swapStacks(target, target.inventory.currentItem, InventoryIndexes.Offhand.value)
        return true
    }
}
