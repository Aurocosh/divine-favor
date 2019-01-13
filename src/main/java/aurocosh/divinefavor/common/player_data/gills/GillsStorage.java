package aurocosh.divinefavor.common.player_data.gills;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class GillsStorage implements Capability.IStorage<IGillsHandler> {
    private static final String TAG_TIME_OUTSIDE_WATER = "TimeOutsideWater";

    @Override
    public NBTBase writeNBT(Capability<IGillsHandler> capability, IGillsHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_TIME_OUTSIDE_WATER, instance.getTicks());
        return tag;
    }

    @Override
    public void readNBT(Capability<IGillsHandler> capability, IGillsHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setTime(tag.getInteger(TAG_TIME_OUTSIDE_WATER));
    }
}
