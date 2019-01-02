package aurocosh.divinefavor.common.util;


import java.util.ArrayList;
import java.util.List;

public class UtilGeneric {
    public static <T> List<T> selectRandom(List<T> list, int count){

        int size = list.size();
        count = UtilMath.clamp(count,0, size);
        int dirsToRemove = size - count;

        List<T> listShallowCopy = new ArrayList<>(list);
        for (int i = 0; i < dirsToRemove; i++) {
            int index = UtilRandom.nextIntExclusive(0, listShallowCopy.size());
            listShallowCopy.remove(index);
        }
        return listShallowCopy;
    }
}
