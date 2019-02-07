package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class GrudgeDataSerializer implements INbtSerializer<GrudgeData> {
    private static String TAG_GRUDGE = "Grudge";

    @Override
    public void serialize(NBTTagCompound nbt, GrudgeData instance) {
        int value = nbt.getInteger(TAG_GRUDGE);
        instance.setMobTypeId(value);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, GrudgeData instance) {
        nbt.setInteger(TAG_GRUDGE,instance.getMobTypeId());
    }
}
