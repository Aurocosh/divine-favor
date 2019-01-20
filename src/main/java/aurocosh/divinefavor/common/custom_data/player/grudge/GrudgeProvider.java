package aurocosh.divinefavor.common.custom_data.player.grudge;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GrudgeProvider implements ICapabilitySerializable<NBTTagCompound> {
    IGrudgeHandler instance = GrudgeDataHandler.CAPABILITY_GRUDGE.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == GrudgeDataHandler.CAPABILITY_GRUDGE;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? GrudgeDataHandler.CAPABILITY_GRUDGE.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) GrudgeDataHandler.CAPABILITY_GRUDGE.getStorage().writeNBT(GrudgeDataHandler.CAPABILITY_GRUDGE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        GrudgeDataHandler.CAPABILITY_GRUDGE.getStorage().readNBT(GrudgeDataHandler.CAPABILITY_GRUDGE, instance, null, nbt);
    }
}
