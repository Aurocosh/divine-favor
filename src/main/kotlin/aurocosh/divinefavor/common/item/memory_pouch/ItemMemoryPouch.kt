package aurocosh.divinefavor.common.item.memory_pouch

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchProvider
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchStorage
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.util.UtilItem.actionResult
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.ICapabilityProvider

class ItemMemoryPouch : ModItem("memory_pouch", "memory_pouch", ConstMainTabOrder.CONTAINERS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if(player.isSneaking){
            player.openGui(DivineFavor, ConstGuiIDs.MEMORY_POUCH, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
            return actionResult(true, stack)
        }

        //TODO
        return actionResult(false, stack)
    }

    override fun initCapabilities(item: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return if (item.item === ModItems.memory_pouch) MemoryPouchProvider() else null
    }

    override fun getShareTag(): Boolean {
        return true
    }

    override fun getNBTShareTag(stack: ItemStack): NBTTagCompound? {
        var tag = super.getNBTShareTag(stack)
        if (tag == null)
            tag = NBTTagCompound()

        val pouch = stack.cap(CAPABILITY_MEMORY_POUCH)
        val tagShare = MemoryPouchStorage.getNbtBase(pouch)
        tag.setTag(TAG_SHARE, tagShare)
        return tag
    }

    override fun readNBTShareTag(stack: ItemStack, nbt: NBTTagCompound?) {
        super.readNBTShareTag(stack, nbt)
        if (nbt == null)
            return

        val pouch = stack.cap(CAPABILITY_MEMORY_POUCH)
        val tagShare = nbt.getCompoundTag(TAG_SHARE)
        MemoryPouchStorage.readNbtBase(pouch, tagShare)
    }

    companion object {
        const val SLOT_COUNT = 27
        private const val TAG_SHARE = "MemoryPouch"
    }
}

