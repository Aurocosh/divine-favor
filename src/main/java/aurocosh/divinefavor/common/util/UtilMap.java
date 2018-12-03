package aurocosh.divinefavor.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilMap {
    /**
     * Method generates such map that all distinct values in values will be keys in a map and each key will point to index.
     * Basically all distinct values in list will be assigned an index.
     * @param values
     * @param <T>
     * @return
     */
    public static <T> Map<T, Integer> generateDistinctValueToIndexMap(List<T> values) {
        int nextIndex = 0;
        Map<T,Integer> map = new HashMap<>();
        for (T value : values)
            if (!map.containsKey(value))
                map.put(value, nextIndex++);
        return map;
    }

    public static <T,K> Map<K, T> reverseMap(Map<T, K> map) {
        Map<K, T> reversedMap = new HashMap<>();
        map.forEach((name, index) -> reversedMap.put(index,name));
        return reversedMap;
    }

    public static <T> int[] generateValueIndexArray(List<T> values, Map<T, Integer> indexMap) {
        int[] indexArray = new int[values.size()];
        for (int i = 0; i < values.size(); i++)
            indexArray[i] = indexMap.get(values.get(i));
        return indexArray;
    }
}
