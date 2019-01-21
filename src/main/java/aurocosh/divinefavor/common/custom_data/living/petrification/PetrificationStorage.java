package aurocosh.divinefavor.common.custom_data.living.petrification;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class PetrificationStorage implements Capability.IStorage<IPetrificationHandler> {
    private static final String TAG_STAND_TICKS = "standTicks";

    @Override
    public NBTBase writeNBT(Capability<IPetrificationHandler> capability, IPetrificationHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_STAND_TICKS, instance.getCureTicks());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPetrificationHandler> capability, IPetrificationHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setCureTicks(tag.getInteger(TAG_STAND_TICKS));
    }
}
