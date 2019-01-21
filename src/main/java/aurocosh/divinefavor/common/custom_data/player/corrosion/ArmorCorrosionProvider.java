package aurocosh.divinefavor.common.custom_data.player.corrosion;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ArmorCorrosionProvider implements ICapabilitySerializable<NBTTagCompound> {
    IArmorCorrosionStatusHandler instance = ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION.getStorage().writeNBT(ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION.getStorage().readNBT(ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION, instance, null, nbt);
    }
}
