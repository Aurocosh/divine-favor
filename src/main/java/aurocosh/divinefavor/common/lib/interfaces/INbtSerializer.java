package aurocosh.divinefavor.common.lib.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtSerializer<T> {
    void serialize(NBTTagCompound nbt, T instance);
    void deserialize(NBTTagCompound nbt, T instance);
}
