package aurocosh.divinefavor.common.item.tools

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.block.doppel.BlockEtherealGoo
import aurocosh.divinefavor.common.block.doppel.TileEtherealGoo
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyIBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemEtherealBrush : ModItem("ethereal_brush", "ethereal_brush", ConstMainTabOrder.TOOLS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (world.isRemote)
            return EnumActionResult.PASS

        val itemStack = player.getHeldItem(hand)

        if (player.isSneaking) {
            val stateTemplate = getStateTemplate(player, world, pos, hand) ?: return EnumActionResult.PASS
            itemStack.set(state, stateTemplate)
            return EnumActionResult.SUCCESS
        } else {
            val tileEntity = world.getTileEntity(pos) as? TileEtherealGoo ?: return EnumActionResult.PASS
            val blockState = itemStack.get(state)
            tileEntity.blockState = blockState
            tileEntity.actualBlockState = blockState
            return EnumActionResult.SUCCESS
        }
    }

    private fun getStateTemplate(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand): IBlockState? {
        val blockState = world.getBlockState(pos)
        val block = blockState.block
        val tileEntity = world.getTileEntity(pos)
        if (block is BlockEtherealGoo) {
            val tile = tileEntity as? TileEtherealGoo
            return tile?.actualBlockState ?: block.defaultState
        }

        if (tileEntity != null)
            return null
        return blockState
    }


    companion object {
        val state by lazy { StackPropertyIBlockState("state", ModBlocks.ethereal_goo.defaultState, showInTooltip = false, showInGui = false, orderIndex = 0) }
    }
}