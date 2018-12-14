package aurocosh.divinefavor.common.player_data.grudge;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class GrudgeStorage implements Capability.IStorage<IGrudgeHandler> {
    private static String TAG_MOB_TYPE = "MobType";

    @Override
    public NBTBase writeNBT (Capability<IGrudgeHandler> capability, IGrudgeHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT (Capability<IGrudgeHandler> capability, IGrudgeHandler instance, EnumFacing side, NBTBase nbt) {
        setDataFromNBT(instance, (NBTTagCompound) nbt);
    }

    public static NBTTagCompound getNbtTagCompound(IGrudgeHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_MOB_TYPE,instance.getMobTypeId());
        return tag;
    }

    public static void setDataFromNBT(IGrudgeHandler instance, NBTTagCompound nbt) {
        int value = nbt.getInteger(TAG_MOB_TYPE);
        instance.setMobTypeId(value);
    }
}
