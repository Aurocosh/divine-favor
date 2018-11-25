package aurocosh.divinefavor.common.util;

import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class UtilSerialize {
    public static int[] SerializeBlockPosArray(ArrayList<BlockPos> posArrayList){
        int[] array = new int[posArrayList.size() * 3];
        int i = 0;
        for (BlockPos pos : posArrayList) {
            array[i++] = pos.getX();
            array[i++] = pos.getY();
            array[i++] = pos.getZ();
        }

        return array;
    }

    public static ArrayList<BlockPos> DeserializeBlockPosArray(int[] array){
        int posCount = array.length / 3;
        ArrayList<BlockPos> posArrayList = new ArrayList<>();

        for (int i = 0, j = 0; i < posCount; i++)
        {
            int x = array[j++];
            int y = array[j++];
            int z = array[j++];
            posArrayList.add(new BlockPos(x,y,z));
        }
        return posArrayList;
    }
}
