package aurocosh.divinefavor.common.custom_data.player.pearl_crumbs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PearlCrumbsProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPearlCrumbsHandler instance = PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS.getStorage().writeNBT(PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS.getStorage().readNBT(PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS, instance, null, nbt);
    }
}
