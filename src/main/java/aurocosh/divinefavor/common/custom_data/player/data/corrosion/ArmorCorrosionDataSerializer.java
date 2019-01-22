package aurocosh.divinefavor.common.custom_data.player.data.corrosion;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

// Handles the actual read/write of the nbt.
public class ArmorCorrosionDataSerializer implements INbtSerializer<ArmorCorrosionData> {
    private static final String TAG_CORRODED_SLOTS = "CorrodedSlots";

    @Override
    public void serialize(NBTTagCompound nbt, ArmorCorrosionData instance) {
        List<Integer> slots = instance.getCorrodedArmorSlots();
        int[] slotsArray = new int[slots.size()];
        for (int i = 0; i < slots.size(); i++)
            slotsArray[i] = slots.get(i);
        nbt.setIntArray(TAG_CORRODED_SLOTS, slotsArray);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ArmorCorrosionData instance) {
        int[] intArray = nbt.getIntArray(TAG_CORRODED_SLOTS);
        List<Integer> slots = new ArrayList<>(intArray.length);
        for (int slot : intArray)
            slots.add(slot);
        instance.setCorrodedArmorSlots(slots);
    }
}
