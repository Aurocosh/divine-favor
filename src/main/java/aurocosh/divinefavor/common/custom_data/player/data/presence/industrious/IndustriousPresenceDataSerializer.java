package aurocosh.divinefavor.common.custom_data.player.data.presence.industrious;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class IndustriousPresenceDataSerializer implements INbtSerializer<IndustriousPresenceData> {
    private static final String TAG_ORE_TO_BREAK = "OreToBreak";

    @Override
    public void serialize(NBTTagCompound nbt, IndustriousPresenceData instance) {
        nbt.setInteger(TAG_ORE_TO_BREAK, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, IndustriousPresenceData instance) {
        instance.setCount(nbt.getInteger(TAG_ORE_TO_BREAK));
    }
}
