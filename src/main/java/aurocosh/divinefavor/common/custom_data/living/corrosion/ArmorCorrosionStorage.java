package aurocosh.divinefavor.common.custom_data.living.corrosion;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

// Handles the actual read/write of the nbt.
public class ArmorCorrosionStorage implements Capability.IStorage<IArmorCorrosionStatusHandler> {
    private static final String TAG_CORRODED_SLOTS = "CorrodedSlots";

    @Override
    public NBTBase writeNBT(Capability<IArmorCorrosionStatusHandler> capability, IArmorCorrosionStatusHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();

        List<Integer> slots = instance.getCorrodedArmorSlots();
        int[] slotsArray = new int[slots.size()];
        for (int i = 0; i < slots.size(); i++)
            slotsArray[i] = slots.get(i);
        tag.setIntArray(TAG_CORRODED_SLOTS, slotsArray);
        return tag;
    }

    @Override
    public void readNBT(Capability<IArmorCorrosionStatusHandler> capability, IArmorCorrosionStatusHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        int[] intArray = tag.getIntArray(TAG_CORRODED_SLOTS);
        List<Integer> slots = new ArrayList<>(intArray.length);
        for (int slot : intArray)
            slots.add(slot);
        instance.setCorrodedArmorSlots(slots);
    }
}
