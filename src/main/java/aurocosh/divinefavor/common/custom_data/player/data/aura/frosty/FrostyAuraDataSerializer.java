package aurocosh.divinefavor.common.custom_data.player.data.aura.frosty;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class FrostyAuraDataSerializer implements INbtSerializer<FrostyAuraData> {
    private static final String TAG_TIME_IS_SNOW_BIOME = "TimeInSnowBiome";

    @Override
    public void serialize(NBTTagCompound nbt, FrostyAuraData instance) {
        nbt.setInteger(TAG_TIME_IS_SNOW_BIOME, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, FrostyAuraData instance) {
        instance.setCount(nbt.getInteger(TAG_TIME_IS_SNOW_BIOME));
    }
}
