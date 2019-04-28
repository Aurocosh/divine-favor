package aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class SpellBowStorage : Capability.IStorage<ISpellBowHandler> {

    override fun writeNBT(capability: Capability<ISpellBowHandler>, instance: ISpellBowHandler, side: EnumFacing?): NBTBase? {
        return getNbtBase(instance)
    }

    override fun readNBT(capability: Capability<ISpellBowHandler>, instance: ISpellBowHandler, side: EnumFacing?, nbt: NBTBase) {
        readNbtBase(instance, nbt as NBTTagCompound)
    }

    companion object {
        private val TAG_SELECTED_SLOT = "SelectedSlot"
        private val TAG_INVENTORY = "Inventory"

        fun getNbtBase(instance: ISpellBowHandler): NBTTagCompound {
            val tag = NBTTagCompound()
            tag.setInteger(TAG_SELECTED_SLOT, instance.selectedSlotIndex)
            tag.setTag(TAG_INVENTORY, instance.getStackHandler().serializeNBT())
            return tag
        }

        fun readNbtBase(instance: ISpellBowHandler, nbt: NBTTagCompound) {
            instance.selectedSlotIndex = nbt.getInteger(TAG_SELECTED_SLOT)
            instance.getStackHandler().deserializeNBT(nbt.getCompoundTag(TAG_INVENTORY))
        }
    }


}
