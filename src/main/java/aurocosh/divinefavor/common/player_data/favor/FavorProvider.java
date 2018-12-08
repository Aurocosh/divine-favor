package aurocosh.divinefavor.common.player_data.favor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FavorProvider implements ICapabilitySerializable<NBTTagCompound> {
    IFavorHandler instance = FavorDataHandler.CAPABILITY_FAVOR.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == FavorDataHandler.CAPABILITY_FAVOR;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? FavorDataHandler.CAPABILITY_FAVOR.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) FavorDataHandler.CAPABILITY_FAVOR.getStorage().writeNBT(FavorDataHandler.CAPABILITY_FAVOR, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        FavorDataHandler.CAPABILITY_FAVOR.getStorage().readNBT(FavorDataHandler.CAPABILITY_FAVOR, instance, null, nbt);
    }
}
