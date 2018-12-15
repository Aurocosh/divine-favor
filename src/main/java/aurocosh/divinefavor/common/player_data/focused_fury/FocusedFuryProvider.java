package aurocosh.divinefavor.common.player_data.focused_fury;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FocusedFuryProvider implements ICapabilitySerializable<NBTTagCompound> {
    IFocusedFuryHandler instance = FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY.getStorage().writeNBT(FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY.getStorage().readNBT(FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY, instance, null, nbt);
    }
}
