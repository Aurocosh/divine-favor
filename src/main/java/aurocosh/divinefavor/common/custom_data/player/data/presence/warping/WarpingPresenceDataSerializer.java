package aurocosh.divinefavor.common.custom_data.player.data.presence.warping;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class WarpingPresenceDataSerializer implements INbtSerializer<WarpingPresenceData> {
    private static final String TAG_WARPING_CHANCE = "WarppingChance";

    @Override
    public void serialize(NBTTagCompound nbt, WarpingPresenceData instance) {
        nbt.setFloat(TAG_WARPING_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, WarpingPresenceData instance) {
        instance.setChance(nbt.getFloat(TAG_WARPING_CHANCE));
    }
}
