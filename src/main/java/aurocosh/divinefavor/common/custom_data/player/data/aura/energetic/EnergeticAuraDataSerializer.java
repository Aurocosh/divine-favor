package aurocosh.divinefavor.common.custom_data.player.data.aura.energetic;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class EnergeticAuraDataSerializer implements INbtSerializer<EnergeticAuraData> {
    private static final String TAG_ENERGETIC_CHANCE = "EnergeticChance";

    @Override
    public void serialize(NBTTagCompound nbt, EnergeticAuraData instance) {
        nbt.setFloat(TAG_ENERGETIC_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, EnergeticAuraData instance) {
        instance.setChance(nbt.getFloat(TAG_ENERGETIC_CHANCE));
    }
}
