package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.entity.projectile.EntityIceArrow
import aurocosh.divinefavor.common.item.base.ModItemArrow
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

open class ItemIceArrow : ModItemArrow("ice_arrow", "ice_arrow", ConstMainTabOrder.BLENDS) {

    init {
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun createArrow(worldIn: World, stack: ItemStack, shooter: EntityLivingBase): EntityArrow {
        return EntityIceArrow(worldIn, shooter)
    }
}

