package aurocosh.divinefavor.common.custom_data.living.limp_leg;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class LimpLegStorage implements Capability.IStorage<ILimpLegHandler> {
    private static final String TAG_SNEAK_TICKS = "sneakTicks";

    @Override
    public NBTBase writeNBT(Capability<ILimpLegHandler> capability, ILimpLegHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_SNEAK_TICKS, instance.getCurrentTicks());
        return tag;
    }

    @Override
    public void readNBT(Capability<ILimpLegHandler> capability, ILimpLegHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setCurrentTicks(tag.getInteger(TAG_SNEAK_TICKS));
    }
}
