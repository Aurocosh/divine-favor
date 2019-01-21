package aurocosh.divinefavor.common.custom_data.living.potion_status;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PotionStatusProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPotionStatusHandler instance = PotionStatusDataHandler.CAPABILITY_POTION_STATUS.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PotionStatusDataHandler.CAPABILITY_POTION_STATUS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? PotionStatusDataHandler.CAPABILITY_POTION_STATUS.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PotionStatusDataHandler.CAPABILITY_POTION_STATUS.getStorage().writeNBT(PotionStatusDataHandler.CAPABILITY_POTION_STATUS, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PotionStatusDataHandler.CAPABILITY_POTION_STATUS.getStorage().readNBT(PotionStatusDataHandler.CAPABILITY_POTION_STATUS, instance, null, nbt);
    }
}
