package aurocosh.divinefavor.common.item.talisman.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

// Delegates all of the system calls to the capability.
public class Provider implements ICapabilitySerializable<NBTTagCompound> {
    ITalismanCostHandler instance = TalismanDataHandler.CAPABILITY_TALISMAN_COST.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == TalismanDataHandler.CAPABILITY_TALISMAN_COST;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? TalismanDataHandler.CAPABILITY_TALISMAN_COST.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) TalismanDataHandler.CAPABILITY_TALISMAN_COST.getStorage().writeNBT(TalismanDataHandler.CAPABILITY_TALISMAN_COST, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        TalismanDataHandler.CAPABILITY_TALISMAN_COST.getStorage().readNBT(TalismanDataHandler.CAPABILITY_TALISMAN_COST, instance, null, nbt);
    }
}
