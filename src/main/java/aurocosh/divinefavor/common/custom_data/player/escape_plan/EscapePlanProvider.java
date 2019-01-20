package aurocosh.divinefavor.common.custom_data.player.escape_plan;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EscapePlanProvider implements ICapabilitySerializable<NBTTagCompound> {
    IEscapePlanHandler instance = EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN.getStorage().writeNBT(EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN.getStorage().readNBT(EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN, instance, null, nbt);
    }
}
