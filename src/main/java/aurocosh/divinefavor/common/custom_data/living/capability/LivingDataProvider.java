package aurocosh.divinefavor.common.custom_data.living.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class LivingDataProvider implements ICapabilitySerializable<NBTTagCompound> {
    ILivingDataHandler instance = LivingDataDataHandler.CAPABILITY_LIVING_DATA.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == LivingDataDataHandler.CAPABILITY_LIVING_DATA;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? LivingDataDataHandler.CAPABILITY_LIVING_DATA.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) LivingDataDataHandler.CAPABILITY_LIVING_DATA.getStorage().writeNBT(LivingDataDataHandler.CAPABILITY_LIVING_DATA, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        LivingDataDataHandler.CAPABILITY_LIVING_DATA.getStorage().readNBT(LivingDataDataHandler.CAPABILITY_LIVING_DATA, instance, null, nbt);
    }
}
