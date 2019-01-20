package aurocosh.divinefavor.common.custom_data.living.limp_leg;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WindLeashProvider implements ICapabilitySerializable<NBTTagCompound> {
    ILimpLegHandler instance = LimpLegDataHandler.CAPABILITY_LIMP_LEG.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == LimpLegDataHandler.CAPABILITY_LIMP_LEG;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? LimpLegDataHandler.CAPABILITY_LIMP_LEG.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) LimpLegDataHandler.CAPABILITY_LIMP_LEG.getStorage().writeNBT(LimpLegDataHandler.CAPABILITY_LIMP_LEG, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        LimpLegDataHandler.CAPABILITY_LIMP_LEG.getStorage().readNBT(LimpLegDataHandler.CAPABILITY_LIMP_LEG, instance, null, nbt);
    }
}
