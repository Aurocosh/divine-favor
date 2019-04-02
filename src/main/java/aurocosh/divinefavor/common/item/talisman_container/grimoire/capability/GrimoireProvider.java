package aurocosh.divinefavor.common.item.talisman_container.grimoire.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class GrimoireProvider implements ICapabilitySerializable<NBTTagCompound> {
    IGrimoireHandler instance = GrimoireDataHandler.CAPABILITY_GRIMOIRE.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == GrimoireDataHandler.CAPABILITY_GRIMOIRE)
            return true;
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(!hasCapability(capability,facing))
            return null;
        if(capability == GrimoireDataHandler.CAPABILITY_GRIMOIRE)
            return GrimoireDataHandler.CAPABILITY_GRIMOIRE.cast(instance);
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(instance.getStackHandler());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) GrimoireDataHandler.CAPABILITY_GRIMOIRE.getStorage().writeNBT(GrimoireDataHandler.CAPABILITY_GRIMOIRE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        GrimoireDataHandler.CAPABILITY_GRIMOIRE.getStorage().readNBT(GrimoireDataHandler.CAPABILITY_GRIMOIRE, instance, null, nbt);
    }
}
