package aurocosh.divinefavor.common.custom_data.living.wind_leash;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class WindLeashStorage implements Capability.IStorage<IWindLeashHandler> {
    private static final String TAG_WIND_LEASH_X = "WindLeashX";
    private static final String TAG_WIND_LEASH_Z = "WindLeashZ";

    @Override
    public NBTBase writeNBT(Capability<IWindLeashHandler> capability, IWindLeashHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        Vec3d vec3d = instance.getVector();
        tag.setDouble(TAG_WIND_LEASH_X, vec3d.x);
        tag.setDouble(TAG_WIND_LEASH_Z, vec3d.z);
        return tag;
    }

    @Override
    public void readNBT(Capability<IWindLeashHandler> capability, IWindLeashHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        double x = tag.getDouble(TAG_WIND_LEASH_X);
        double z = tag.getDouble(TAG_WIND_LEASH_Z);
        instance.setVector(new Vec3d(x, 0, z));
    }
}
