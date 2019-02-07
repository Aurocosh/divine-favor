package aurocosh.divinefavor.common.custom_data.player.data.hunters_aura;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class HuntersAuraDataSerializer implements INbtSerializer<HuntersAuraData> {
    private static final String TAG_HUNTERS_CHANCE = "HuntersChance";

    @Override
    public void serialize(NBTTagCompound nbt, HuntersAuraData instance) {
        nbt.setFloat(TAG_HUNTERS_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, HuntersAuraData instance) {
        instance.setChance(nbt.getFloat(TAG_HUNTERS_CHANCE));
    }
}
