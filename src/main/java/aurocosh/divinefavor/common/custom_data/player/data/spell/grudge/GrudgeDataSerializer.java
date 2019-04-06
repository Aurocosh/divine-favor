package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

// Handles the actual read/write of the nbt.
public class GrudgeDataSerializer implements INbtSerializer<GrudgeData> {
    private static String TAG_GRUDGE = "Grudge";

    @Override
    public void serialize(NBTTagCompound nbt, GrudgeData instance) {
        nbt.setString(TAG_GRUDGE, instance.getMobTypeId().toString());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, GrudgeData instance) {
        String value = nbt.getString(TAG_GRUDGE);
        instance.setMobTypeId(new ResourceLocation(value));
    }
}
