package aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class SuffocatingFumesDataSerializer implements INbtSerializer<SuffocatingFumesData> {
    private static final String TAG_SUFFOCATING_FUME_Y = "SuffocatingFumeY";

    @Override
    public void serialize(NBTTagCompound nbt, SuffocatingFumesData instance) {
        nbt.setInteger(TAG_SUFFOCATING_FUME_Y, instance.getY());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, SuffocatingFumesData instance) {
        instance.setY(nbt.getInteger(TAG_SUFFOCATING_FUME_Y));
    }
}
