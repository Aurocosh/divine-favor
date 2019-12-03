package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class PotionLiquidWalking(name: String, private val canWalkOnBlock: (World, BlockPos)->(Boolean)) : ModPotion(name, 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        tickLiquidWalk(livingBase, canWalkOnBlock)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
