package aurocosh.divinefavor.common.item.compressed_item_drop

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

open class ItemCompressedItemsDrop : ModItem("compressed_items_drop", "compressed_items_drop", ConstGemTabOrder.OTHER_GEMS) {

    init {
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {

        val tileEntity = world.getTileEntity(pos) ?: return EnumActionResult.PASS
        if(world.isRemote)
            return EnumActionResult.SUCCESS

        val tileItemHandler = tileEntity.capNull(ITEM_HANDLER_CAPABILITY) ?: return EnumActionResult.SUCCESS
        val stack = player.getHeldItem(hand)
        val itemHandler = stack.cap(ITEM_HANDLER_CAPABILITY)
        val slotsToMove = itemHandler.asSlotSequence().filter { it.stack.isNotEmpty() }.toList()
        tileItemHandler.moveItemsFromSlots(slotsToMove)
        checkIfEmpty(itemHandler, stack)
        return EnumActionResult.SUCCESS
    }

    override fun onItemRightClick(world: World?, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)

        val itemsToMove = if (player.isSneaking) {
            player.getInventoryCapability().asSequence().count(ItemStack::isEmpty)
        } else {
            val playerHasEmptySlot = player.getInventoryCapability().asSequence().any(ItemStack::isEmpty)
            if (playerHasEmptySlot) 1 else 0
        }

        val itemHandler = stack.cap(ITEM_HANDLER_CAPABILITY)
        val slotsToMove = itemHandler.asSlotSequence()
                .filter { it.stack.isNotEmpty() }
                .take(itemsToMove)
                .toList()

        slotsToMove.forEach { itemHandler.extractItem(it) }
        slotsToMove.forEach { UtilPlayer.addStackToInventoryOrDrop(player, it.stack) }

        checkIfEmpty(itemHandler, stack)
        return ActionResult(EnumActionResult.SUCCESS, stack)
    }

    private fun checkIfEmpty(itemHandler: IItemHandler, stack: ItemStack) {
        val isEmpty = !itemHandler.asSequence().any(ItemStack::isNotEmpty)
        if (isEmpty)
            stack.shrink(stack.count)
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.compressed_items_drop) CompressedItemsDropProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val inventory = (stack.getCapability<IItemHandler>(ITEM_HANDLER_CAPABILITY, null) as ItemStackHandler?)!!
        tag.setTag(ShareTagName, inventory.serializeNBT())
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return
        val inventory = (stack.getCapability<IItemHandler>(ITEM_HANDLER_CAPABILITY, null) as ItemStackHandler?)!!
        inventory.deserializeNBT(nbt.getCompoundTag(ShareTagName))
    }

    companion object {
        const val InventorySize = 27
        private const val ShareTagName = "Ritual"
    }
}

