package aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class ManipulativePresenceDataSerializer implements INbtSerializer<ManipulativePresenceData> {
    private static final String TAG_MANIPULATIVE_CHANCE = "ManipulativeChance";

    @Override
    public void serialize(NBTTagCompound nbt, ManipulativePresenceData instance) {
        nbt.setFloat(TAG_MANIPULATIVE_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ManipulativePresenceData instance) {
        instance.setChance(nbt.getFloat(TAG_MANIPULATIVE_CHANCE));
    }
}
