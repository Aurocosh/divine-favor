package aurocosh.divinefavor.common.item.talisman_container.grimoire.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class GrimoireProvider implements ICapabilitySerializable<NBTTagCompound> {
    IGrimoireHandler instance = GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE().getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE())
            return true;
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(!hasCapability(capability,facing))
            return null;
        if(capability == GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE())
            return GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE().cast(instance);
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(instance.getStackHandler());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE().getStorage().writeNBT(GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE(), instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE().getStorage().readNBT(GrimoireDataHandler.INSTANCE.getCAPABILITY_GRIMOIRE(), instance, null, nbt);
    }
}
