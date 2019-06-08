package aurocosh.divinefavor.common.item.gems

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.checkForTag
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class ItemInviteMarker(name: String, private val canTeleportToDimensions: Boolean) : ModItem(name, name, ConstGemTabOrder.OTHER_GEMS) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val teleported = !teleportPlayer(stack, player, world)
        return if (teleported) ActionResult(EnumActionResult.PASS, stack) else ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY)
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        val teleported = !teleportPlayer(stack, player, world)
        return if (teleported) EnumActionResult.PASS else EnumActionResult.SUCCESS
    }

    private fun teleportPlayer(stack: ItemStack, player: EntityPlayer?, world: World): Boolean {
        if (world.isRemote)
            return false
        if (stack.isEmpty)
            return false
        if (stack.item !is ItemInviteMarker)
            return false
        if (!stack.checkForTag(TAG_PLAYER_UUID))
            return false

        val uuid = UUID.fromString(stack.compound.getString(TAG_PLAYER_UUID))
        val server = world.minecraftServer ?: return false
        val playerList = server.playerList
        val targetPlayer = playerList.getPlayerByUUID(uuid)
        if (player == null)
            return false

        if (!canTeleportToDimensions && targetPlayer.dimension != player.dimension)
            return false
        UtilEntity.teleport(player, targetPlayer.dimension, targetPlayer.position)
        UtilPlayer.damageStack(player, stack)
        stack.shrink(1)
        return true
    }

    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        if (stack.checkForTag(TAG_PLAYER_UUID))
            tooltip.add(I18n.format("item.divinefavor:contract.player", stack.compound.getString(TAG_PLAYER_UUID)))
    }

    companion object {
        var TAG_PLAYER_UUID = "PlayerUUID"
    }
}