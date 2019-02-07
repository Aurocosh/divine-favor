package aurocosh.divinefavor.common.custom_data.player.data.aura.visceral;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class VisceralAuraDataSerializer implements INbtSerializer<VisceralAuraData> {
    private static final String TAG_VISCERAL_CHANCE = "VisceralChance";

    @Override
    public void serialize(NBTTagCompound nbt, VisceralAuraData instance) {
        nbt.setFloat(TAG_VISCERAL_CHANCE, instance.getChance());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, VisceralAuraData instance) {
        instance.setChance(nbt.getFloat(TAG_VISCERAL_CHANCE));
    }
}
