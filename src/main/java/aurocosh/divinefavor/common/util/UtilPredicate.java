package aurocosh.divinefavor.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class UtilPredicate {
    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        List<Predicate<T>> predicatesList = Arrays.asList(predicates);
        return UtilList.aggregate(predicatesList, Predicate::and);
    }

    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<T>... predicates) {
        List<Predicate<T>> predicatesList = Arrays.asList(predicates);
        return UtilList.aggregate(predicatesList, Predicate::or);
    }
}
