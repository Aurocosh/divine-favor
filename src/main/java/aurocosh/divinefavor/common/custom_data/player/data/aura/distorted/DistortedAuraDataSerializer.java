package aurocosh.divinefavor.common.custom_data.player.data.aura.distorted;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class DistortedAuraDataSerializer implements INbtSerializer<DistortedAuraData> {
    private static final String TAG_NEAR_ENDERMAN_CHANCE = "NearEndermanChance";

    @Override
    public void serialize(NBTTagCompound nbt, DistortedAuraData instance) {
        nbt.setFloat(TAG_NEAR_ENDERMAN_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, DistortedAuraData instance) {
        instance.setChance(nbt.getFloat(TAG_NEAR_ENDERMAN_CHANCE));
    }
}
