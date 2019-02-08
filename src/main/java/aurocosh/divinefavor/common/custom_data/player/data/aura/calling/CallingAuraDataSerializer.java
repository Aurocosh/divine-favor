package aurocosh.divinefavor.common.custom_data.player.data.aura.calling;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class CallingAuraDataSerializer implements INbtSerializer<CallingAuraData> {
    private static final String TAG_CALLING_CHANCE = "CallingChance";

    @Override
    public void serialize(NBTTagCompound nbt, CallingAuraData instance) {
        nbt.setFloat(TAG_CALLING_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, CallingAuraData instance) {
        instance.setChance(nbt.getFloat(TAG_CALLING_CHANCE));
    }
}
