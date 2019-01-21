package aurocosh.divinefavor.common.custom_data.living.data.petrification;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class PetrificationDataSerializer implements INbtSerializer<PetrificationData> {
    private static final String TAG_PETRIFICATION_CURE_TICKS = "PetrificationCureTicks";

    @Override
    public void serialize(NBTTagCompound nbt, PetrificationData instance) {
        nbt.setInteger(TAG_PETRIFICATION_CURE_TICKS, instance.getCureTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, PetrificationData instance) {
        instance.setCureTicks(nbt.getInteger(TAG_PETRIFICATION_CURE_TICKS));
    }
}
