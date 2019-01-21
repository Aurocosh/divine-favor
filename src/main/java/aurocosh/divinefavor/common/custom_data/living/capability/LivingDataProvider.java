package aurocosh.divinefavor.common.custom_data.living.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class LivingDataProvider implements ICapabilitySerializable<NBTTagCompound> {
    ILivingDataHandler instance = LivingDataDataHandler.CAPABILITY_POTION_STATUS.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == LivingDataDataHandler.CAPABILITY_POTION_STATUS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? LivingDataDataHandler.CAPABILITY_POTION_STATUS.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) LivingDataDataHandler.CAPABILITY_POTION_STATUS.getStorage().writeNBT(LivingDataDataHandler.CAPABILITY_POTION_STATUS, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        LivingDataDataHandler.CAPABILITY_POTION_STATUS.getStorage().readNBT(LivingDataDataHandler.CAPABILITY_POTION_STATUS, instance, null, nbt);
    }
}
