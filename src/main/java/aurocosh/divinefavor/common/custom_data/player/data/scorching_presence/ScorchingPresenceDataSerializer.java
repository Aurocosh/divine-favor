package aurocosh.divinefavor.common.custom_data.player.data.scorching_presence;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class ScorchingPresenceDataSerializer implements INbtSerializer<ScorchingPresenceData> {
    private static final String TAG_SCORCHING_CHANCE = "ScorchingChance";

    @Override
    public void serialize(NBTTagCompound nbt, ScorchingPresenceData instance) {
        nbt.setFloat(TAG_SCORCHING_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ScorchingPresenceData instance) {
        instance.setChance(nbt.getFloat(TAG_SCORCHING_CHANCE));
    }
}
