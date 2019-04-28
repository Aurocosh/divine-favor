package aurocosh.divinefavor.common.item.contract_binder

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

class ItemContractBinder : ModItem("contract_binder", "contract_binder", ConstMainTabOrder.CONTAINERS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemRightClick(worldIn: World?, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val itemStackIn = playerIn.getHeldItem(hand)
        if (hand == EnumHand.OFF_HAND)
            return ActionResult(EnumActionResult.PASS, itemStackIn)
        playerIn.openGui(DivineFavor, ConstGuiIDs.CONTRACT_BINDER, worldIn!!, playerIn.posX.toInt(), playerIn.posY.toInt(), playerIn.posZ.toInt())
        return ActionResult(EnumActionResult.SUCCESS, itemStackIn)
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.contract_binder) CintractBinderProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val inventory = (stack.getCapability<IItemHandler>(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) as ItemStackHandler?)!!
        tag.setTag(TAG_SHARE, inventory.serializeNBT())
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return
        val inventory = (stack.getCapability<IItemHandler>(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) as ItemStackHandler?)!!
        inventory.deserializeNBT(nbt.getCompoundTag(TAG_SHARE))
    }

    companion object {
        val SIZE = 7
        private val TAG_SHARE = "Binder"
    }
}

