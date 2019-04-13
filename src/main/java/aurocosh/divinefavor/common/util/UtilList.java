package aurocosh.divinefavor.common.util;

import java.util.ArrayList;
import java.util.List;

public class UtilList {
    @FunctionalInterface
    public interface Predicate<T> {
        boolean select(T element);
    }

    @FunctionalInterface
    public interface ComparePredicate<T> {
        boolean isFirstBetter(T first, T second);
    }

    @FunctionalInterface
    public interface Processor<T> {
        void process(T element);
    }

    public static <T> boolean isAll(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (!predicate.select(element))
                return false;
        return true;
    }

    public static <T> boolean isAny(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (predicate.select(element))
                return true;
        return false;
    }

    public static <T> List<T> filterList(List<T> list, Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (T element : list)
            if (predicate.select(element))
                filtered.add(element);
        return filtered;
    }

    public static <T, K extends T> List<K> filterListByClass(List<T> list, Class<K> clazz) {
        List<K> filtered = new ArrayList<>();
        for (T element : list)
            if (clazz.isInstance(element))
                filtered.add((K) element);
        return filtered;
    }

    public static <T> T findFirst(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (predicate.select(element))
                return element;
        return null;
    }

    public static <T> T pickBest(List<T> list, ComparePredicate<T> predicate) {
        T best = null;
        for (T element : list)
            if (best == null || predicate.isFirstBetter(element, best))
                best = element;
        return best;
    }

    public static <T> int findIndex(List<T> list, Predicate<T> predicate) {
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            if (predicate.select(element))
                return i;
        }
        return -1;
    }
}
