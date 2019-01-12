package aurocosh.divinefavor.common.util;

import java.util.ArrayList;
import java.util.List;

public class UtilList {
    @FunctionalInterface
    public interface Predicate<T> {
        boolean select(T element);
    }

    public static <T> List<T> filterList(List<T> list, Predicate<T> predicate){
        List<T> filtered = new ArrayList<>();
        for (T element : list)
            if (predicate.select(element))
                filtered.add(element);
        return filtered;
    }
}
