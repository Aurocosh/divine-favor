package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase

class PotionLiquidWalking(name: String, private val block: Block) : ModPotion(name, true, 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        tickLiquidWalk(livingBase, block)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
