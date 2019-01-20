package aurocosh.divinefavor.common.custom_data.player.focused_fury;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class FocusedFuryStorage implements Capability.IStorage<IFocusedFuryHandler> {
    private static String TAG_FOCUSED_FURY = "FocusedFury";

    @Override
    public NBTBase writeNBT (Capability<IFocusedFuryHandler> capability, IFocusedFuryHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT (Capability<IFocusedFuryHandler> capability, IFocusedFuryHandler instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        int value = tag.getInteger(TAG_FOCUSED_FURY);
        instance.setMobTypeId(value);
    }

    public static NBTTagCompound getNbtTagCompound(IFocusedFuryHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(TAG_FOCUSED_FURY,instance.getMobTypeId());
        return tag;
    }
}
