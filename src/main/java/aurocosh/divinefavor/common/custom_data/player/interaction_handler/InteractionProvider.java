package aurocosh.divinefavor.common.custom_data.player.interaction_handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class InteractionProvider implements ICapabilitySerializable<NBTTagCompound> {
    IInteractionHandler instance = InteractionDataHandler.CAPABILITY_INTERACTION.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == InteractionDataHandler.CAPABILITY_INTERACTION;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? InteractionDataHandler.CAPABILITY_INTERACTION.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) InteractionDataHandler.CAPABILITY_INTERACTION.getStorage().writeNBT(InteractionDataHandler.CAPABILITY_INTERACTION, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        InteractionDataHandler.CAPABILITY_INTERACTION.getStorage().readNBT(InteractionDataHandler.CAPABILITY_INTERACTION, instance, null, nbt);
    }
}
