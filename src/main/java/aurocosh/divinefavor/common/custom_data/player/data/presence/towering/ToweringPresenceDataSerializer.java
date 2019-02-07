package aurocosh.divinefavor.common.custom_data.player.data.presence.towering;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class ToweringPresenceDataSerializer implements INbtSerializer<ToweringPresenceData> {
    private static final String TAG_CURSE_TIME = "CurseTime";

    @Override
    public void serialize(NBTTagCompound nbt, ToweringPresenceData instance) {
        nbt.setInteger(TAG_CURSE_TIME, instance.getCurseTime());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ToweringPresenceData instance) {
        instance.setCurseTime(nbt.getInteger(TAG_CURSE_TIME));
    }
}
