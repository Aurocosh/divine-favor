package aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability

import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.ISpellBladeHandler
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class SpellBladeStorage : Capability.IStorage<ISpellBladeHandler> {

    override fun writeNBT(capability: Capability<ISpellBladeHandler>, instance: ISpellBladeHandler, side: EnumFacing?): NBTBase? {
        return getNbtBase(instance)
    }

    override fun readNBT(capability: Capability<ISpellBladeHandler>, instance: ISpellBladeHandler, side: EnumFacing?, nbt: NBTBase) {
        readNbtBase(instance, nbt as NBTTagCompound)
    }

    companion object {
        private const val TAG_SELECTED_SLOT = "SelectedSlot"
        private const val TAG_INVENTORY = "Inventory"

        fun getNbtBase(instance: ISpellBladeHandler): NBTTagCompound {
            val tag = NBTTagCompound()
            tag.setInteger(TAG_SELECTED_SLOT, instance.selectedSlotIndex)
            tag.setTag(TAG_INVENTORY, instance.getStackHandler().serializeNBT())
            return tag
        }

        fun readNbtBase(instance: ISpellBladeHandler, nbt: NBTTagCompound) {
            instance.selectedSlotIndex = nbt.getInteger(TAG_SELECTED_SLOT)
            instance.getStackHandler().deserializeNBT(nbt.getCompoundTag(TAG_INVENTORY))
        }
    }


}
