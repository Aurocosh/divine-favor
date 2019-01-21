package aurocosh.divinefavor.common.custom_data.living.data.wind_leash;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class WindLeashDataSerializer implements INbtSerializer<WindLeashData> {
    private static final String TAG_WIND_LEASH_X = "WindLeashX";
    private static final String TAG_WIND_LEASH_Z = "WindLeashZ";

    @Override
    public void serialize(NBTTagCompound nbt, WindLeashData instance) {
        Vec3d vector = instance.getVector();
        nbt.setDouble(TAG_WIND_LEASH_X, vector.x);
        nbt.setDouble(TAG_WIND_LEASH_Z, vector.z);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, WindLeashData instance) {
        double x = nbt.getDouble(TAG_WIND_LEASH_X);
        double z = nbt.getDouble(TAG_WIND_LEASH_Z);
        instance.setVector(new Vec3d(x, 0, z));
    }
}
