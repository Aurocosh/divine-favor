package aurocosh.divinefavor.common.custom_data.world.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WorldDataProvider implements ICapabilitySerializable<NBTTagCompound> {
    IWorldDataHandler instance = WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE.getStorage().writeNBT(WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE.getStorage().readNBT(WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE, instance, null, nbt);
    }
}
