package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.SlotData
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFlyingCapability
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory

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
}
