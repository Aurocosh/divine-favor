package aurocosh.divinefavor.common.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class UtilBlockPos {
    public static long[] serializePositions(List<BlockPos> posList) {
        long[] blockPositionsSerialized = new long[posList.size()];
        for (int i = 0; i < posList.size(); i++)
            blockPositionsSerialized[i] = posList.get(i).toLong();
        return blockPositionsSerialized;
    }

    public static List<BlockPos> deserializePositions(long[] serializedValues) {
        List<BlockPos> posList = new ArrayList<>(serializedValues.length);
        for (long serializedValue : serializedValues)
            posList.add(BlockPos.fromLong(serializedValue));
        return posList;
    }
}
