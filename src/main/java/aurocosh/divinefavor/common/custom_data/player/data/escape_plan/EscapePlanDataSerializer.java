package aurocosh.divinefavor.common.custom_data.player.data.escape_plan;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

// Handles the actual read/write of the nbt.
public class EscapePlanDataSerializer implements INbtSerializer<EscapePlanData> {
    private static String TAG_ESCAPE_POSITION = "EscapePosition";
    private static String TAG_ESCAPE_DIMENSION = "EscapeDimension";

    @Override
    public void serialize(NBTTagCompound nbt, EscapePlanData instance) {
        BlockPos pos = BlockPos.fromLong(nbt.getLong(TAG_ESCAPE_POSITION));
        int dimension = nbt.getInteger(TAG_ESCAPE_DIMENSION);
        instance.setGlobalPosition(new GlobalBlockPos(pos, dimension));
    }

    @Override
    public void deserialize(NBTTagCompound nbt, EscapePlanData instance) {
        GlobalBlockPos pos = instance.getGlobalPosition();
        nbt.setLong(TAG_ESCAPE_POSITION, pos.pos.toLong());
        nbt.setInteger(TAG_ESCAPE_DIMENSION, pos.dimensionId);
    }
}
