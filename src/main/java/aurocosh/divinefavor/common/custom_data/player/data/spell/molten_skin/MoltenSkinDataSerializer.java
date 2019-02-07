package aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class MoltenSkinDataSerializer implements INbtSerializer<MoltenSkinData> {
    private static final String TAG_TIME_OUTSIDE_LAVA = "TimeOutsideLava";

    @Override
    public void serialize(NBTTagCompound nbt, MoltenSkinData instance) {
        nbt.setInteger(TAG_TIME_OUTSIDE_LAVA, instance.getTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, MoltenSkinData instance) {
        instance.setTime(nbt.getInteger(TAG_TIME_OUTSIDE_LAVA));
    }
}
