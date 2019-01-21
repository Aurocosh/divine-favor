package aurocosh.divinefavor.common.custom_data.player.crawling_mist;

import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class CrawlingMistStorage implements Capability.IStorage<ICrawlingMistHandler> {
    private static final String TAG_MIST_ORIGIN = "MistOrigin";

    @Override
    public NBTBase writeNBT(Capability<ICrawlingMistHandler> capability, ICrawlingMistHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        UtilNbt.setBlockPos(tag, TAG_MIST_ORIGIN, instance.getMistOrigin());
        return tag;
    }

    @Override
    public void readNBT(Capability<ICrawlingMistHandler> capability, ICrawlingMistHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setMistOrigin(UtilNbt.getBlockPos(tag, TAG_MIST_ORIGIN));
    }
}
