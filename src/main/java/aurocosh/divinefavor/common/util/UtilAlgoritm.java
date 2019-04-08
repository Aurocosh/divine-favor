package aurocosh.divinefavor.common.util;

import java.util.function.Supplier;

public class UtilAlgoritm {
    public static boolean repeatUntilSuccessful(Supplier<Boolean> lambda, int successesRequired, int attemptsAllowed) {
        while (attemptsAllowed-- > 0 && successesRequired > 0) {
            if (lambda.get())
                successesRequired--;
        }
        return successesRequired == 0;
    }
}
