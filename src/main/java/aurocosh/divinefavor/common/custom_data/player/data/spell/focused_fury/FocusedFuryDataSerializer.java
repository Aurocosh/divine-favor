package aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

// Handles the actual read/write of the nbt.
public class FocusedFuryDataSerializer implements INbtSerializer<FocusedFuryData> {
    private static String TAG_FOCUSED_FURY = "FocusedFury";

    @Override
    public void serialize(NBTTagCompound nbt, FocusedFuryData instance) {
        nbt.setString(TAG_FOCUSED_FURY, instance.getMobTypeId().toString());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, FocusedFuryData instance) {
        String value = nbt.getString(TAG_FOCUSED_FURY);
        instance.setMobTypeId(new ResourceLocation(value));
    }
}
