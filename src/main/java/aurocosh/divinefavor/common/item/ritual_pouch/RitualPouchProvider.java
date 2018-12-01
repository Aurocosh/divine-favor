package aurocosh.divinefavor.common.item.ritual_pouch;

import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class RitualPouchProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound> {
    private final ItemStackHandler inventory;

    public RitualPouchProvider() {
        inventory = new ItemStackHandler( 7 );
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return inventory.serializeNBT();
    }

    @Override
    public void deserializeNBT( NBTTagCompound nbt ) {
        inventory.deserializeNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing ) {
        if( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ) {
            return true;
        }
        return false;
    }

    @Override
    public <T> T getCapability( Capability<T> capability, EnumFacing facing ) {
        if( capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ) {
            return (T) inventory;
        }
        return null;
    }
}