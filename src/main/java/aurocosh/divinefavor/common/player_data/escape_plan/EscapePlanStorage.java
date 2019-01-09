package aurocosh.divinefavor.common.player_data.escape_plan;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class EscapePlanStorage implements Capability.IStorage<IEscapePlanHandler> {
    private static String TAG_ESCAPE_POSITION = "EscapePosition";
    private static String TAG_ESCAPE_DIMENSION = "EscapeDimension";

    @Override
    public NBTBase writeNBT (Capability<IEscapePlanHandler> capability, IEscapePlanHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT (Capability<IEscapePlanHandler> capability, IEscapePlanHandler instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setPosition(BlockPos.fromLong(tag.getLong(TAG_ESCAPE_POSITION)));
        instance.setDimension(tag.getInteger(TAG_ESCAPE_DIMENSION));
    }

    public static NBTTagCompound getNbtTagCompound(IEscapePlanHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setLong(TAG_ESCAPE_POSITION,instance.getPosition().toLong());
        tag.setInteger(TAG_ESCAPE_DIMENSION,instance.getDimension());
        return tag;
    }
}
