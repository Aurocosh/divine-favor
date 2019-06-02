package aurocosh.divinefavor.common.lib;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;
// Parameter capability is not supposed to be null but sometimes it is. I cannot change parameter nullability in kotlin, so that is what this class for.
public interface ICapabilitySerializableAnnotationFix<T extends NBTBase> extends ICapabilitySerializable<T> {
    @Override
    boolean hasCapability(@Nullable Capability<?> capability, @Nullable EnumFacing enumFacing);

    @Nullable
    @Override
    <K> K getCapability(@Nullable Capability<K> capability, @Nullable EnumFacing enumFacing);
}
