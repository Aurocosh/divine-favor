package aurocosh.divinefavor.common.custom_data.living.data.cripple;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class CrippleDataSerializer implements INbtSerializer<CrippleData> {
    private static final String TAG_CRIPPLE_CURE_TICKS = "CrippleCureTicks";

    @Override
    public void serialize(NBTTagCompound nbt, CrippleData instance) {
        nbt.setInteger(TAG_CRIPPLE_CURE_TICKS, instance.getCureTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, CrippleData instance) {
        instance.setCureTicks(nbt.getInteger(TAG_CRIPPLE_CURE_TICKS));
    }
}
