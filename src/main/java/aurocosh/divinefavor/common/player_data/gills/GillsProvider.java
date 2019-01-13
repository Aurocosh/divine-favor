package aurocosh.divinefavor.common.player_data.gills;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GillsProvider implements ICapabilitySerializable<NBTTagCompound> {
    IGillsHandler instance = GillsDataHandler.CAPABILITY_GILLS.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == GillsDataHandler.CAPABILITY_GILLS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? GillsDataHandler.CAPABILITY_GILLS.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) GillsDataHandler.CAPABILITY_GILLS.getStorage().writeNBT(GillsDataHandler.CAPABILITY_GILLS, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        GillsDataHandler.CAPABILITY_GILLS.getStorage().readNBT(GillsDataHandler.CAPABILITY_GILLS, instance, null, nbt);
    }
}
