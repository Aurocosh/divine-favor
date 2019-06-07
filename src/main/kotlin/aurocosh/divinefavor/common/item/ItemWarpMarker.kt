package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.lib.extensions.hasKey
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemWarpMarker(name: String, private val canTeleportToDimensions: Boolean) : ModItem(name, name, ConstGemTabOrder.OTHER_GEMS) {

    init {
        setMaxStackSize(64)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        return if (!teleportPlayer(stack, player, world)) ActionResult(EnumActionResult.PASS, stack) else ActionResult(EnumActionResult.SUCCESS, stack)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        return if (!teleportPlayer(stack, player, world)) EnumActionResult.PASS else EnumActionResult.SUCCESS
    }

    private fun teleportPlayer(stack: ItemStack, player: EntityPlayer, world: World): Boolean {
        if (world.isRemote)
            return false
        if (stack.isEmpty)
            return false
        if (stack.item !is ItemWarpMarker)
            return false
        val tag = stack.compound
        if (!tag.hasKey(TAG_POSITION, TAG_DIMENSION))
            return false

        val destination = tag.getBlockPos(TAG_POSITION)
        val dimension = tag.getInteger(TAG_DIMENSION)
        if (!canTeleportToDimensions && dimension != player.dimension)
            return false

        UtilEntity.teleport(player, dimension, destination)
        stack.shrink(1)
        return true
    }

    companion object {
        const val TAG_POSITION = "Position"
        const val TAG_DIMENSION = "Dimension"
    }
}