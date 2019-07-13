package aurocosh.divinefavor.common.item.tools

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.block.doppel.BlockEtherealGoo
import aurocosh.divinefavor.common.block.doppel.TileEtherealGoo
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.sendStatusMessage
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyIBlockState
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import aurocosh.divinefavor.common.util.UtilItem.actionResultPass
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilStatus
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class ItemEtherealBrush : ModItem("ethereal_brush", "ethereal_brush", ConstMainTabOrder.TOOLS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
        addPropertyOverride(ResourceLocation("painting")) { stack, _, _ -> if (stack.get(isPainting)) 1f else 0f }
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (world.isRemote)
            return EnumActionResult.SUCCESS

        val stack = player.getHeldItem(hand)

        if (player.isSneaking) {
            val stateTemplate = getStateTemplate(world, pos) ?: return EnumActionResult.PASS
            stack.set(state, stateTemplate)
            return EnumActionResult.SUCCESS
        }

        val painting = stack.get(isPainting)
        val success = if (painting) paint(world, pos, stack) else place(player, world, pos, stack, facing)
        return actionResultPass(success)
    }


    private fun place(player: EntityPlayer, world: World, pos: BlockPos, stack: ItemStack, facing: EnumFacing): Boolean {
        val replaceable = UtilBlock.isAirOrReplaceable(world, pos)
        val finalPos = if (replaceable) pos else pos.offset(facing)
        if (!UtilBlock.isAirOrReplaceable(world, finalPos))
            return false

        val presentGoo = UtilPlayer.countRequiredGoo(player, 1)
        if (presentGoo == 0)
            return false
        val state = stack.get(state)
        val success = UtilBlock.replaceBlockWithGoo(player, world, finalPos, state)
        if (success)
            UtilPlayer.consumeGoo(player, 1)
        return success
    }

    private fun paint(world: World, pos: BlockPos, stack: ItemStack): Boolean {
        val tileEntity = world.getTileEntity(pos) as? TileEtherealGoo ?: return false
        val blockState = stack.get(state)
        tileEntity.blockState = blockState
        tileEntity.actualBlockState = blockState
        return true
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (player.isSneaking) {
            switchMode(player, stack)
            return actionResult(true, stack)
        }
        return actionResult(false, stack)
    }

    private fun switchMode(player: EntityPlayer, stack: ItemStack) {
        val painting = !stack.get(isPainting)
        stack.set(isPainting, painting)
        if (!player.world.isRemote)
            return
        if (painting)
            player.sendStatusMessage("message.divinefavor.brush.painting_goo", UtilStatus.formatString(TextFormatting.AQUA), true)
        else
            player.sendStatusMessage("message.divinefavor.brush.placing_goo", UtilStatus.formatString(TextFormatting.AQUA), true)
    }

    private fun getStateTemplate(world: World, pos: BlockPos): IBlockState? {
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
        val isPainting = StackPropertyBool("painting", true, showInTooltip = false, showInGui = false, orderIndex = 0)
        val state by lazy { StackPropertyIBlockState("state", ModBlocks.ethereal_goo.defaultState, showInTooltip = false, showInGui = false, orderIndex = 0) }
    }
}