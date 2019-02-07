package aurocosh.divinefavor.common.custom_data.player.data.aura.charred;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class CharredAuraDataSerializer implements INbtSerializer<CharredAuraData> {
    private static final String TAG_FIRES_TO_IGNITE = "FiresToIgnite";

    @Override
    public void serialize(NBTTagCompound nbt, CharredAuraData instance) {
        nbt.setInteger(TAG_FIRES_TO_IGNITE, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, CharredAuraData instance) {
        instance.setCount(nbt.getInteger(TAG_FIRES_TO_IGNITE));
    }
}
