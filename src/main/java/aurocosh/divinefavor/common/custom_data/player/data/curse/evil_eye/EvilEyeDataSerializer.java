package aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class EvilEyeDataSerializer implements INbtSerializer<EvilEyeData> {
    private static final String TAG_EVIL_EYE_SEVERITY = "EVIL_EYE_SEVERITY";

    @Override
    public void serialize(NBTTagCompound nbt, EvilEyeData instance) {
        nbt.setInteger(TAG_EVIL_EYE_SEVERITY, instance.getSeverity());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, EvilEyeData instance) {
        instance.setSeverity(nbt.getInteger(TAG_EVIL_EYE_SEVERITY));
    }
}
