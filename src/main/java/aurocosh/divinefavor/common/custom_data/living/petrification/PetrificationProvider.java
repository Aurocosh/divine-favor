package aurocosh.divinefavor.common.custom_data.living.petrification;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PetrificationProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPetrificationHandler instance = PetrificationDataHandler.CAPABILITY_PETRIFICATION.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PetrificationDataHandler.CAPABILITY_PETRIFICATION;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? PetrificationDataHandler.CAPABILITY_PETRIFICATION.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PetrificationDataHandler.CAPABILITY_PETRIFICATION.getStorage().writeNBT(PetrificationDataHandler.CAPABILITY_PETRIFICATION, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PetrificationDataHandler.CAPABILITY_PETRIFICATION.getStorage().readNBT(PetrificationDataHandler.CAPABILITY_PETRIFICATION, instance, null, nbt);
    }
}
