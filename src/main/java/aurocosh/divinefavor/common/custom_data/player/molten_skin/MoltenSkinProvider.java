package aurocosh.divinefavor.common.custom_data.player.molten_skin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MoltenSkinProvider implements ICapabilitySerializable<NBTTagCompound> {
    IMoltenSkinHandler instance = MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN.getStorage().writeNBT(MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN.getStorage().readNBT(MoltenSkinDataHandler.CAPABILITY_MOLTEN_SKIN, instance, null, nbt);
    }
}
