package aurocosh.divinefavor.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class UtilList {
    @FunctionalInterface
    public interface ListExtractor<T, K> {
        List<T> extract(K value);
    }

    @FunctionalInterface
    public interface ComparePredicate<T> {
        boolean isFirstBetter(T first, T second);
    }

    public static <T> boolean isAll(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (!predicate.test(element))
                return false;
        return true;
    }

    public static <T> void filterList(List<T> list, Predicate<T> predicate) {
        for (int i = list.size() - 1; i >= 0; i--)
            if (predicate.test(list.get(i)))
                list.remove(i);
    }

    public static <T> boolean isAny(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (predicate.test(element))
                return true;
        return false;
    }

    public static <T> List<T> select(List<T> list, Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (T element : list)
            if (predicate.test(element))
                filtered.add(element);
        return filtered;
    }

    public static <T, K> List<K> select(Collection<T> list, Class<K> clazz) {
        List<K> filtered = new ArrayList<>();
        for (T element : list)
            if (clazz.isInstance(element))
                filtered.add(clazz.cast(element));
        return filtered;
    }

    public static <T> T findFirst(List<T> list, Predicate<T> predicate) {
        for (T element : list)
            if (predicate.test(element))
                return element;
        return null;
    }

    public static <T, K> K findFirst(List<T> list, Class<K> clazz, Predicate<K> predicate) {
        for (T element : list) {
            if (clazz.isInstance(element)) {
                K value = clazz.cast(element);
                if (predicate.test(value))
                    return value;
            }
        }
        return null;
    }

    public static <T, K> List<T> aggregate(List<K> list, ListExtractor<T, K> extractor) {
        List<T> values = new ArrayList<>();
        for (K element : list)
            values.addAll(extractor.extract(element));
        return values;
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
            if (predicate.test(element))
                return i;
        }
        return -1;
    }
}
