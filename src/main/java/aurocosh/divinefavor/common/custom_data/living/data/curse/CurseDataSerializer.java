package aurocosh.divinefavor.common.custom_data.living.data.curse;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class CurseDataSerializer implements INbtSerializer<CurseData> {
    private static final String TAG_CURSE_COUNT = "CurseCount";

    @Override
    public void serialize(NBTTagCompound nbt, CurseData instance) {
        nbt.setInteger(TAG_CURSE_COUNT, instance.getCurseCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, CurseData instance) {
        instance.setCurseCount(nbt.getInteger(TAG_CURSE_COUNT));
    }
}
