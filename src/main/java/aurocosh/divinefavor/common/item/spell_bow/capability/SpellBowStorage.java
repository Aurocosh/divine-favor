package aurocosh.divinefavor.common.item.spell_bow.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class SpellBowStorage implements Capability.IStorage<ISpellBowHandler> {
    private static String TAG_SELECTED_SLOT = "SelectedSlot";
    private static String TAG_INVENTORY = "Inventory";

    @Override
    public NBTBase writeNBT (Capability<ISpellBowHandler> capability, ISpellBowHandler instance, EnumFacing side) {
        return getNbtBase(instance);
    }

    @Override
    public void readNBT (Capability<ISpellBowHandler> capability, ISpellBowHandler instance, EnumFacing side, NBTBase nbt) {
        readNbtBase(instance, (NBTTagCompound) nbt);
    }

    public static NBTTagCompound getNbtBase(ISpellBowHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_SELECTED_SLOT,instance.getSelectedSlotIndex());
        tag.setTag(TAG_INVENTORY,instance.getStackHandler().serializeNBT());
        return tag;
    }

    public static void readNbtBase(ISpellBowHandler instance, NBTTagCompound nbt) {
        NBTTagCompound tag = nbt;
        instance.setSelectedSlotIndex(tag.getInteger(TAG_SELECTED_SLOT));
        instance.getStackHandler().deserializeNBT(tag.getCompoundTag(TAG_INVENTORY));
    }


}
