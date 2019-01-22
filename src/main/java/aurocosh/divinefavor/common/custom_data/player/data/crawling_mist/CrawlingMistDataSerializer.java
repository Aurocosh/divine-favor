package aurocosh.divinefavor.common.custom_data.player.data.crawling_mist;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class CrawlingMistDataSerializer implements INbtSerializer<CrawlingMistData> {
    private static final String TAG_MIST_ORIGIN = "MistOrigin";

    @Override
    public void serialize(NBTTagCompound nbt, CrawlingMistData instance) {
        UtilNbt.setBlockPos(nbt, TAG_MIST_ORIGIN, instance.getMistOrigin());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, CrawlingMistData instance) {
        instance.setMistOrigin(UtilNbt.getBlockPos(nbt, TAG_MIST_ORIGIN));
    }
}
