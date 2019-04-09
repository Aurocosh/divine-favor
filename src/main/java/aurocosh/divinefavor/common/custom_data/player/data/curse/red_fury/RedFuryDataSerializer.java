package aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.nbt.NBTTagCompound;

public class RedFuryDataSerializer implements INbtSerializer<RedFuryData> {
    private static final String TAG_RED_FURY = "RedFury";
    private static final String TAG_VECTOR = "Vector";

    @Override
    public void serialize(NBTTagCompound nbt, RedFuryData instance) {
        NBTTagCompound compound = new NBTTagCompound();
        UtilNbt.setVec3d(compound, TAG_VECTOR, instance.getVector());
        nbt.setTag(TAG_RED_FURY, compound);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, RedFuryData instance) {
        if (!nbt.hasKey(TAG_RED_FURY))
            return;
        NBTTagCompound compound = (NBTTagCompound) nbt.getTag(TAG_RED_FURY);
        instance.setVector(UtilNbt.getVec3d(compound, TAG_VECTOR));
    }
}
