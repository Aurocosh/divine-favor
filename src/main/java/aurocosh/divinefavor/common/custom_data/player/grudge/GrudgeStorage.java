package aurocosh.divinefavor.common.custom_data.player.grudge;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class GrudgeStorage implements Capability.IStorage<IGrudgeHandler> {
    private static String TAG_GRUDGE = "Grudge";

    @Override
    public NBTBase writeNBT (Capability<IGrudgeHandler> capability, IGrudgeHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT (Capability<IGrudgeHandler> capability, IGrudgeHandler instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        int value = tag.getInteger(TAG_GRUDGE);
        instance.setMobTypeId(value);
    }

    public static NBTTagCompound getNbtTagCompound(IGrudgeHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_GRUDGE,instance.getMobTypeId());
        return tag;
    }
}
