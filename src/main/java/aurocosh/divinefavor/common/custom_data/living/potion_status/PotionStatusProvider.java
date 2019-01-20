package aurocosh.divinefavor.common.custom_data.living.potion_status;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PotionStatusProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPotionStatusHandler instance = PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS.getStorage().writeNBT(PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS.getStorage().readNBT(PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS, instance, null, nbt);
    }
}
