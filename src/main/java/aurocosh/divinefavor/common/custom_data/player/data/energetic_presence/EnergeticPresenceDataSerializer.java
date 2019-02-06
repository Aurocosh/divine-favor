package aurocosh.divinefavor.common.custom_data.player.data.energetic_presence;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class EnergeticPresenceDataSerializer implements INbtSerializer<EnergeticPresenceData> {
    private static final String TAG_TIME_RUNNING_ON_WATER = "TimeRunningOnWater";

    @Override
    public void serialize(NBTTagCompound nbt, EnergeticPresenceData instance) {
        nbt.setInteger(TAG_TIME_RUNNING_ON_WATER, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, EnergeticPresenceData instance) {
        instance.setCount(nbt.getInteger(TAG_TIME_RUNNING_ON_WATER));
    }
}
