package aurocosh.divinefavor.common.custom_data.player.data.spell.gills;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class GillsDataSerializer implements INbtSerializer<GillsData> {
    private static final String TAG_TIME_OUTSIDE_WATER = "TimeOutsideWater";

    @Override
    public void serialize(NBTTagCompound nbt, GillsData instance) {
        nbt.setInteger(TAG_TIME_OUTSIDE_WATER, instance.getTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, GillsData instance) {
        instance.setTime(nbt.getInteger(TAG_TIME_OUTSIDE_WATER));
    }
}
