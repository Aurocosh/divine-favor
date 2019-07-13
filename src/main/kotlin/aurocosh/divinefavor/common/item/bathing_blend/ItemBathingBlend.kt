package aurocosh.divinefavor.common.item.bathing_blend

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ItemBathingBlend(name: String, val duration: Int, val rate: Int) : ModItem("blend_$name", "blends/$name", ConstMainTabOrder.BLENDS) {

    init {
        creativeTab = DivineFavor.TAB_MAIN
    }

    open fun applyEffect(livingBase: EntityLivingBase) {}
    open fun convertBlocks(world: World, heaterPos: BlockPos, coordinates: List<BlockPos>) {}

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}