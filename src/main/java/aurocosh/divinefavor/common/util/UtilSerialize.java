package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UtilSerialize {
    public static int[] serializeBlockPosArray(Collection<BlockPos> posArrayList) {
        int[] array = new int[posArrayList.size() * 3];
        int i = 0;
        for (BlockPos pos : posArrayList) {
            array[i++] = pos.getX();
            array[i++] = pos.getY();
            array[i++] = pos.getZ();
        }

        return array;
    }

    public static ArrayList<BlockPos> deserializeBlockPosArray(int[] array) {
        int posCount = array.length / 3;
        ArrayList<BlockPos> posArrayList = new ArrayList<>();

        for (int i = 0, j = 0; i < posCount; i++) {
            int x = array[j++];
            int y = array[j++];
            int z = array[j++];
            posArrayList.add(new BlockPos(x, y, z));
        }
        return posArrayList;
    }

    public static int[] serializeGlobalBlockPosArray(List<GlobalBlockPos> posArrayList) {
        int[] array = new int[posArrayList.size() * 4];
        int i = 0;
        for (GlobalBlockPos gpos : posArrayList) {
            array[i++] = gpos.pos.getX();
            array[i++] = gpos.pos.getY();
            array[i++] = gpos.pos.getZ();
            array[i++] = gpos.dimensionId;
        }

        return array;
    }

    public static ArrayList<GlobalBlockPos> deserializeGlobalBlockPosArray(int[] array) {
        int posCount = array.length / 4;
        ArrayList<GlobalBlockPos> posArrayList = new ArrayList<>();

        for (int i = 0, j = 0; i < posCount; i++) {
            int x = array[j++];
            int y = array[j++];
            int z = array[j++];
            int dimension = array[j++];
            posArrayList.add(new GlobalBlockPos(x, y, z, dimension));
        }
        return posArrayList;
    }

    public static UUID stringToUUID(String string) {
        try {
            return UUID.fromString(string);
        }
        catch (IllegalArgumentException var2) {
            return null;
        }
    }
}
