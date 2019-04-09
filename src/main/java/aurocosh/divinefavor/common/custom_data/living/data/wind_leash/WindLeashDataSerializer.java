package aurocosh.divinefavor.common.custom_data.living.data.wind_leash;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.nbt.NBTTagCompound;

public class WindLeashDataSerializer implements INbtSerializer<WindLeashData> {
    private static final String TAG_WIND_LEASH = "WindLeash";

    @Override
    public void serialize(NBTTagCompound nbt, WindLeashData instance) {
        UtilNbt.setVec3d(nbt, TAG_WIND_LEASH, instance.getVector());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, WindLeashData instance) {
        instance.setVector(UtilNbt.getVec3d(nbt, TAG_WIND_LEASH));
    }
}
