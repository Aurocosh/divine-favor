package aurocosh.divinefavor.common.custom_data.player.data.arboreal_aura;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class ArborealAuraDataSerializer implements INbtSerializer<ArborealAuraData> {
    private static final String TAG_WOOD_TO_BREAK = "WoodToBreak";

    @Override
    public void serialize(NBTTagCompound nbt, ArborealAuraData instance) {
        nbt.setInteger(TAG_WOOD_TO_BREAK, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ArborealAuraData instance) {
        instance.setCount(nbt.getInteger(TAG_WOOD_TO_BREAK));
    }
}