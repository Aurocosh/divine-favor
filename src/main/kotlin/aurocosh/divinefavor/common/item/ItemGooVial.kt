package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.sendStatusMessage
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import aurocosh.divinefavor.common.util.UtilItem.actionResultPass
import aurocosh.divinefavor.common.util.UtilStatus
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemGooVial(name: String, val capacity: Int) : ModItem("goo_vial_$name", "goo_vials/$name", ConstMainTabOrder.TOOLS) {
    private val quarterCapacity = capacity / 4;

    init {
        creativeTab = DivineFavor.TAB_MAIN
        addPropertyOverride(ResourceLocation("fullness")) { stack, _, _ -> (stack.get(gooCount) / quarterCapacity).toFloat() }
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        if (player.isSneaking) {
            switchMode(player, stack)
            return EnumActionResult.SUCCESS
        }

        val collecting = stack.get(isCollecting)
        val success = if (collecting) collect(player, world, pos, stack) else place(player, world, pos, stack, facing)
        return actionResultPass(success)
    }

    private fun place(player: EntityPlayer, world: World, pos: BlockPos, stack: ItemStack, facing: EnumFacing): Boolean {
        val replaceable = UtilBlock.isAirOrReplaceable(world, pos)
        val finalPos = if (replaceable) pos else pos.offset(facing)
        if (!UtilBlock.isAirOrReplaceable(world, finalPos))
            return false

        val count = stack.get(gooCount)
        if (count <= 0)
            return false
        val state = ModBlocks.ethereal_goo.defaultState
        val success = UtilBlock.replaceBlock(player, world, finalPos, state)
        if (success)
            stack.set(gooCount, count - 1)
        return success
    }

    private fun collect(player: EntityPlayer, world: World, pos: BlockPos, stack: ItemStack): Boolean {
        val blockState = world.getBlockState(pos)
        if (blockState.block !== ModBlocks.ethereal_goo)
            return false
        val count = stack.get(gooCount)
        if (count >= capacity)
            return false

        val state = Blocks.AIR.defaultState
        val success = UtilBlock.replaceBlock(player, world, pos, state)
        if (success)
            stack.set(gooCount, count + 1)
        return success
    }

    override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (player.isSneaking) {
            switchMode(player, stack)
            return actionResult(true, stack)
        }
        return actionResult(false, stack)
    }

    private fun switchMode(player: EntityPlayer, stack: ItemStack) {
        val collecting = !stack.get(isCollecting)
        stack.set(isCollecting, collecting)
        if (!player.world.isRemote)
            return
        if (collecting)
            player.sendStatusMessage("message.divinefavor.collecting_goo", UtilStatus.formatString(TextFormatting.AQUA))
        else
            player.sendStatusMessage("message.divinefavor.placing_goo", UtilStatus.formatString(TextFormatting.AQUA))
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        val gooCount = stack.get(gooCount)
        tooltip.add(I18n.format("tooltip.divinefavor:goo_vial.count", gooCount))
        tooltip.add(I18n.format("tooltip.divinefavor:goo_vial.capacity", capacity))
        val collecting = stack.get(isCollecting)
        if (collecting)
            tooltip.add(I18n.format("tooltip.divinefavor:goo_vial.collecting", capacity))
        else
            tooltip.add(I18n.format("tooltip.divinefavor:goo_vial.placing", capacity))
    }

    companion object {
        val gooCount = StackPropertyInt("goo_count", 0, 0, Int.MAX_VALUE, showInTooltip = false, showInGui = false, orderIndex = 0)
        val isCollecting = StackPropertyBool("collecting", true, showInTooltip = false, showInGui = false, orderIndex = 0)
    }
}

