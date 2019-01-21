package aurocosh.divinefavor.common.custom_data.living.data.limp_leg;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class LimpLegDataSerializer implements INbtSerializer<LimpLegData> {
    private static final String TAG_LIMP_LEG_CURE_TICKS = "LimpLegCureTicks";

    @Override
    public void serialize(NBTTagCompound nbt, LimpLegData instance) {
        nbt.setInteger(TAG_LIMP_LEG_CURE_TICKS, instance.getCureTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, LimpLegData instance) {
        instance.setCureTicks(nbt.getInteger(TAG_LIMP_LEG_CURE_TICKS));
    }
}
