package aurocosh.divinefavor.common.custom_data.living.potion_status;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PotionStatusStorage implements Capability.IStorage<IPotionStatusHandler> {
    private static final String TAG_CURSE_COUNT = "CurseCount";

    @Override
    public NBTBase writeNBT(Capability<IPotionStatusHandler> capability, IPotionStatusHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_CURSE_COUNT, instance.getCurseCount());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPotionStatusHandler> capability, IPotionStatusHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setCurseCount(tag.getInteger(TAG_CURSE_COUNT));
    }
}
