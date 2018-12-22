package aurocosh.divinefavor.common.player_data.spell_count;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SpellUsesProvider implements ICapabilitySerializable<NBTTagCompound> {
    ISpellUsesHandler instance = SpellUsesDataHandler.CAPABILITY_SPELL_USES.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SpellUsesDataHandler.CAPABILITY_SPELL_USES;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? SpellUsesDataHandler.CAPABILITY_SPELL_USES.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) SpellUsesDataHandler.CAPABILITY_SPELL_USES.getStorage().writeNBT(SpellUsesDataHandler.CAPABILITY_SPELL_USES, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        SpellUsesDataHandler.CAPABILITY_SPELL_USES.getStorage().readNBT(SpellUsesDataHandler.CAPABILITY_SPELL_USES, instance, null, nbt);
    }
}
