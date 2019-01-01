package aurocosh.divinefavor.common.player_data.molten_skin;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class MoltenSkinStorage implements Capability.IStorage<IMoltenSkinHandler> {
    private static final String TAG_TIME_OUTSIDE_LAVA = "TimeOutsideLava";

    @Override
    public NBTBase writeNBT(Capability<IMoltenSkinHandler> capability, IMoltenSkinHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_TIME_OUTSIDE_LAVA, instance.getTicks());
        return tag;
    }

    @Override
    public void readNBT(Capability<IMoltenSkinHandler> capability, IMoltenSkinHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setTime(tag.getInteger(TAG_TIME_OUTSIDE_LAVA));
    }
}
