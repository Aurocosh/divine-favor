package aurocosh.divinefavor.common.custom_data.living.wind_leash;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WindLeashProvider implements ICapabilitySerializable<NBTTagCompound> {
    IWindLeashHandler instance = WindLeashDataHandler.CAPABILITY_WIND_LEASH.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == WindLeashDataHandler.CAPABILITY_WIND_LEASH;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? WindLeashDataHandler.CAPABILITY_WIND_LEASH.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) WindLeashDataHandler.CAPABILITY_WIND_LEASH.getStorage().writeNBT(WindLeashDataHandler.CAPABILITY_WIND_LEASH, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        WindLeashDataHandler.CAPABILITY_WIND_LEASH.getStorage().readNBT(WindLeashDataHandler.CAPABILITY_WIND_LEASH, instance, null, nbt);
    }
}
