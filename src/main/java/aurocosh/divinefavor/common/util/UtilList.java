package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.functional_interfaces.ComparePredicate;
import aurocosh.divinefavor.common.lib.functional_interfaces.ListExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class UtilList {
    @SafeVarargs
    public static <T> List<T> unite(List<T>... lists) {
        int size = 0;
        for (List<T> list : lists)
            size += list.size();

        List<T> list = new ArrayList<>(size);
        for (List<T> subList : lists)
            list.addAll(subList);

        return list;
    }

    public static <T> boolean isAll(Iterable<T> list, Predicate<T> predicate) {
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

    public static <T> List<T> split(List<T> list, Predicate<T> predicate) {
        List<T> selected = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--)
            if (predicate.test(list.get(i)))
                selected.add(list.remove(i));
        return selected;
    }

    public static <T> boolean isAny(Iterable<T> iterable, Predicate<T> predicate) {
        for (T element : iterable)
            if (predicate.test(element))
                return true;
        return false;
    }

    public static <T> List<T> select(Iterable<T> iterable, Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (T element : iterable)
            if (predicate.test(element))
                filtered.add(element);
        return filtered;
    }

    public static <T> List<T> selectFirstN(Iterable<T> iterable, int count) {
        List<T> filtered = new ArrayList<>();
        Iterator<T> iterator = iterable.iterator();
        while (count-- > 0 && iterator.hasNext())
            filtered.add(iterator.next());
        return filtered;
    }

    public static <T, K> List<K> select(Iterable<T> iterable, Class<K> clazz) {
        List<K> filtered = new ArrayList<>();
        for (T element : iterable)
            if (clazz.isInstance(element))
                filtered.add(clazz.cast(element));
        return filtered;
    }

    public static <T> T findFirst(Iterable<T> iterable, Predicate<T> predicate) {
        for (T element : iterable)
            if (predicate.test(element))
                return element;
        return null;
    }

    public static <T, K> K findFirst(Iterable<T> iterable, Class<K> clazz, Predicate<K> predicate) {
        for (T element : iterable) {
            if (clazz.isInstance(element)) {
                K value = clazz.cast(element);
                if (predicate.test(value))
                    return value;
            }
        }
        return null;
    }

    public static <T> T aggregate(Iterable<T> iterable, BiFunction<T, T, T> aggregator) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext())
            return null;
        T value = iterator.next();
        while (iterator.hasNext())
            value = aggregator.apply(value, iterator.next());
        return value;
    }

    public static <T, K> List<T> flatten(Iterable<K> iterable, ListExtractor<T, K> extractor) {
        List<T> values = new ArrayList<>();
        for (K element : iterable)
            values.addAll(extractor.extract(element));
        return values;
    }

    public static <T, K> List<T> process(Iterable<K> iterable, Function<K, T> processor) {
        List<T> values = new ArrayList<>();
        for (K element : iterable)
            values.add(processor.apply(element));
        return values;
    }

    public static <T> T pickBest(List<T> iterable, ComparePredicate<T> predicate) {
        T best = null;
        for (T element : iterable)
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
