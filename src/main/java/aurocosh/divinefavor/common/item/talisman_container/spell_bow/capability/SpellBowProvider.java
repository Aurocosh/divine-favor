package aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class SpellBowProvider implements ICapabilitySerializable<NBTTagCompound> {
    ISpellBowHandler instance = SpellBowDataHandler.CAPABILITY_SPELL_BOW.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == SpellBowDataHandler.CAPABILITY_SPELL_BOW)
            return true;
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(!hasCapability(capability,facing))
            return null;
        if(capability == SpellBowDataHandler.CAPABILITY_SPELL_BOW)
            return SpellBowDataHandler.CAPABILITY_SPELL_BOW.cast(instance);
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(instance.getStackHandler());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) SpellBowDataHandler.CAPABILITY_SPELL_BOW.getStorage().writeNBT(SpellBowDataHandler.CAPABILITY_SPELL_BOW, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        SpellBowDataHandler.CAPABILITY_SPELL_BOW.getStorage().readNBT(SpellBowDataHandler.CAPABILITY_SPELL_BOW, instance, null, nbt);
    }
}
