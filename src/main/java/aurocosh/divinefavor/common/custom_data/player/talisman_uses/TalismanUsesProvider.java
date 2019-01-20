package aurocosh.divinefavor.common.custom_data.player.talisman_uses;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TalismanUsesProvider implements ICapabilitySerializable<NBTTagCompound> {
    ITalismanUsesHandler instance = TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES.getStorage().writeNBT(TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES.getStorage().readNBT(TalismanUsesDataHandler.CAPABILITY_TALISMAN_USES, instance, null, nbt);
    }
}
