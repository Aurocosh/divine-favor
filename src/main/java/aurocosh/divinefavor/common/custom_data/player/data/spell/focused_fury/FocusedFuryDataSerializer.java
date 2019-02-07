package aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class FocusedFuryDataSerializer implements INbtSerializer<FocusedFuryData> {
    private static String TAG_FOCUSED_FURY = "FocusedFury";

    @Override
    public void serialize(NBTTagCompound nbt, FocusedFuryData instance) {
        int value = nbt.getInteger(TAG_FOCUSED_FURY);
        instance.setMobTypeId(value);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, FocusedFuryData instance) {
        nbt.setInteger(TAG_FOCUSED_FURY,instance.getMobTypeId());
    }
}
