package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.SlotData
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.lib.extensions.asSequence
import aurocosh.divinefavor.common.lib.extensions.asSlotSequence
import aurocosh.divinefavor.common.lib.extensions.getAllInventoryCapabilities
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFlyingCapability
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler

data class HeldStack(val hand: EnumHand, val slot: Int, val stack: ItemStack)

object UtilPlayer {

    fun getItemInHand(player: EntityPlayer, predicate: (Item) -> Boolean): ItemStack {
        var stack = player.heldItemMainhand
        if (!stack.isEmpty && predicate.invoke(stack.item))
            return stack
        stack = player.heldItemOffhand
        return if (!stack.isEmpty && predicate.invoke(stack.item)) stack else ItemStack.EMPTY
    }

    fun getHandWithItem(player: EntityPlayer, predicate: (Item) -> Boolean): EnumHand? {
        var stack = player.heldItemMainhand
        if (!stack.isEmpty && predicate.invoke(stack.item))
            return EnumHand.MAIN_HAND
        stack = player.heldItemOffhand
        return if (!stack.isEmpty && predicate.invoke(stack.item)) EnumHand.OFF_HAND else null
    }

    fun getHeldStacks(player: EntityPlayer): List<HeldStack> {
        return listOf(
                HeldStack(EnumHand.MAIN_HAND, player.inventory.currentItem, player.heldItemMainhand),
                HeldStack(EnumHand.OFF_HAND, InventoryIndexes.Offhand.value, player.heldItemOffhand)
        )
    }

    fun getHand(predicate: (EnumHand) -> Boolean): EnumHand? {
        if (predicate.invoke(EnumHand.MAIN_HAND))
            return EnumHand.MAIN_HAND
        return if (predicate.invoke(EnumHand.OFF_HAND)) EnumHand.OFF_HAND else null
    }

    fun findStackInMainInventory(player: EntityPlayer, predicate: (ItemStack) -> Boolean): SlotData {
        for (i in 9 until player.inventory.sizeInventory) {
            val stack = player.inventory.getStackInSlot(i)
            if (predicate.invoke(stack))
                return SlotData(i, stack)
        }
        return SlotData(-1, ItemStack.EMPTY)
    }

    fun findStackInInventory(player: EntityPlayer, predicate: (ItemStack) -> Boolean): SlotData {
        var stack = player.heldItemMainhand
        if (predicate.invoke(stack))
            return SlotData(player.inventory.currentItem, stack)
        stack = player.heldItemOffhand
        if (predicate.invoke(stack))
            return SlotData(InventoryIndexes.Offhand.value, stack)
        for (i in 0 until player.inventory.sizeInventory) {
            stack = player.inventory.getStackInSlot(i)
            if (predicate.invoke(stack))
                return SlotData(i, stack)
        }
        return SlotData(-1, ItemStack.EMPTY)
    }

    fun swapStacks(player: EntityPlayer, firstSlot: Int, secondSlot: Int) {
        val firstStack = player.inventory.getStackInSlot(firstSlot)
        val secondStack = player.inventory.getStackInSlot(secondSlot)
        player.inventory.setInventorySlotContents(firstSlot, secondStack)
        player.inventory.setInventorySlotContents(secondSlot, firstStack)
    }

    fun addStackToInventoryOrDrop(player: EntityPlayer, stack: ItemStack) {
        if (player.inventory.addItemStackToInventory(stack)) {
            val pickupPitch = (UtilRandom.nextFloat(-1f, 1f) * 0.7f + 1.0f) * 2.0f
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, pickupPitch)
        } else {
            val itemEntity = EntityItem(player.world, player.posX, player.posY, player.posZ, stack)
            itemEntity.setPickupDelay(0)
            player.world.spawnEntity(itemEntity)
        }
    }

    fun damageStack(player: EntityPlayer, stack: ItemStack) {
        stack.shrink(1)
        if (stack.isEmpty)
            player.inventory.deleteStack(stack)
    }

    fun getOtherHand(hand: EnumHand): EnumHand {
        return if (hand == EnumHand.MAIN_HAND) EnumHand.OFF_HAND else EnumHand.MAIN_HAND
    }

    fun getHandIndex(player: EntityPlayer, hand: EnumHand): Int {
        return if (hand == EnumHand.MAIN_HAND) player.inventory.currentItem else InventoryIndexes.Offhand.value
    }

    fun setAllowFlying(player: EntityPlayer, allowFlying: Boolean) {
        if (player.capabilities.isCreativeMode)
            return
        if (player.capabilities.allowFlying == allowFlying)
            return
        player.capabilities.allowFlying = allowFlying
        player.capabilities.isFlying = player.capabilities.isFlying && allowFlying
        if (!player.world.isRemote)
            MessageSyncFlyingCapability(allowFlying).sendTo(player)
    }

    fun getSlotRange(): IntRange {
        val minSlot = InventoryIndexes.MinSlotIndex.value
        val maxSlot = InventoryIndexes.MaxSlotIndex.value
        return minSlot..maxSlot
    }

    fun countItems(itemStack: ItemStack, player: EntityPlayer): Int {
        if (player.capabilities.isCreativeMode)
            return Integer.MAX_VALUE

        return player.getAllInventoryCapabilities()
                .flatMap(IItemHandler::asSequence)
                .filter { it.item === itemStack.item && it.metadata == itemStack.metadata }
                .sumBy { it.count }
    }

    fun consumeItems(itemStack: ItemStack, player: EntityPlayer, count: Int, makeSureThereIsEnough: Boolean = true): Boolean {
        if (player.capabilities.isCreativeMode)
            return true
        if (makeSureThereIsEnough && countItems(itemStack, player) < count)
            return false

        val sequence = player.getAllInventoryCapabilities()
                .flatMap(IItemHandler::asSlotSequence)
                .filter { (_, _, stack) -> stack.item === itemStack.item && stack.metadata == itemStack.metadata }
        var toRemove = count

        for ((handler, index, stack) in sequence) {
            val removed = Math.min(toRemove, stack.count)
            toRemove -= removed
            handler.extractItem(index, removed, false)
        }
        return toRemove == 0
    }

    fun getShift(player: EntityPlayer, forwardShift: Int, upShift: Int, rightShift: Int): BlockPos {
        val facing = player.horizontalFacing
        if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
            return BlockPos(-rightShift, upShift, forwardShift)
        return BlockPos(-forwardShift, upShift, -rightShift)
    }

    fun countBlocks(player: EntityPlayer, world: World, state: IBlockState, required: Int = Int.MAX_VALUE): Int {
        val itemStack = UtilBlock.getSilkDropIfPresent(world, state, player)
        val itemCount = countItems(itemStack, player)
        return Math.min(itemCount, required)
    }

    fun consumeBlocks(player: EntityPlayer, world: World, state: IBlockState, count: Int): Boolean {
        val itemStack = UtilBlock.getSilkDropIfPresent(world, state, player)
        return consumeItems(itemStack, player, count)
    }

    data class RelativeDirections(val forward: EnumFacing, val back: EnumFacing, val up: EnumFacing, val down: EnumFacing, val right: EnumFacing, val left: EnumFacing)

    fun getRelativeDirections(player: EntityPlayer, forward: EnumFacing): RelativeDirections {
        val back = forward.opposite
        val vertical = forward.axis == EnumFacing.Axis.Y
        var up = if (vertical) player.horizontalFacing else EnumFacing.UP
        val left = if (vertical) up.rotateY() else forward.rotateYCCW()
        val right = left.opposite
        if (forward == EnumFacing.DOWN)
            up = up.opposite
        val down = up.opposite

        return RelativeDirections(forward, back, up, down, right, left)
    }
}
