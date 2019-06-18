package aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class SpellPickStorage : Capability.IStorage<ISpellPickHandler> {

    override fun writeNBT(capability: Capability<ISpellPickHandler>, instance: ISpellPickHandler, side: EnumFacing?): NBTBase? {
        return getNbtBase(instance)
    }

    override fun readNBT(capability: Capability<ISpellPickHandler>, instance: ISpellPickHandler, side: EnumFacing?, nbt: NBTBase) {
        readNbtBase(instance, nbt as NBTTagCompound)
    }

    companion object {
        private const val TAG_SELECTED_SLOT = "SelectedSlot"
        private const val TAG_INVENTORY = "Inventory"

        fun getNbtBase(instance: ISpellPickHandler): NBTTagCompound {
            val tag = NBTTagCompound()
            tag.setInteger(TAG_SELECTED_SLOT, instance.selectedSlotIndex)
            tag.setTag(TAG_INVENTORY, instance.getStackHandler().serializeNBT())
            return tag
        }

        fun readNbtBase(instance: ISpellPickHandler, nbt: NBTTagCompound) {
            instance.selectedSlotIndex = nbt.getInteger(TAG_SELECTED_SLOT)
            instance.getStackHandler().deserializeNBT(nbt.getCompoundTag(TAG_INVENTORY))
        }
    }


}
