package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.muliblock.IMultiblockController
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemBoneKey : ModItem("bone_key", "bone_key", ConstMainTabOrder.TOOLS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand?, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (worldIn.isRemote)
            return EnumActionResult.PASS

        val entity = worldIn.getTileEntity(pos)
        if (entity !is IMultiblockController)
            return EnumActionResult.PASS

        entity.tryToFormMultiBlock()
        return EnumActionResult.SUCCESS
    }
}