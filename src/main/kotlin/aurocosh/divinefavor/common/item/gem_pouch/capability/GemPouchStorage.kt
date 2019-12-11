package aurocosh.divinefavor.common.item.gem_pouch.capability

import aurocosh.divinefavor.common.nbt_properties.NbtPropertyInt
import aurocosh.divinefavor.common.nbt_properties.NbtPropertyNbtTag
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class GemPouchStorage : Capability.IStorage<IGemPouchHandler> {

    override fun writeNBT(capability: Capability<IGemPouchHandler>, instance: IGemPouchHandler, side: EnumFacing?): NBTBase? {
        return getNbtBase(instance)
    }

    override fun readNBT(capability: Capability<IGemPouchHandler>, instance: IGemPouchHandler, side: EnumFacing?, nbt: NBTBase) {
        readNbtBase(instance, nbt as NBTTagCompound)
    }

    companion object {
        private val selectedSlot = NbtPropertyInt("SelectedSlot", 0, listOf("tag_SelectedSlot"))
        private val inventory = NbtPropertyNbtTag("Inventory", NBTTagCompound(), listOf("tag_Inventory"))

        fun getNbtBase(instance: IGemPouchHandler): NBTTagCompound {
            val tag = NBTTagCompound()
            tag.set(selectedSlot, instance.selectedSlotIndex)
            tag.set(inventory, instance.getStackHandler().serializeNBT())
            return tag
        }

        fun readNbtBase(instance: IGemPouchHandler, nbt: NBTTagCompound) {
            instance.selectedSlotIndex = nbt.get(selectedSlot)
            instance.getStackHandler().deserializeNBT(nbt.get(inventory))
        }
    }
}
