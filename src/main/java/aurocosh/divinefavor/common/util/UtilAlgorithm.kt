package aurocosh.divinefavor.common.util

object UtilAlgorithm {
    fun repeatUntilSuccessful(lambda: () -> Boolean, successesRequired: Int, attemptsAllowed: Int): Boolean {
        var successesCounter = successesRequired
        var attemptsAllowed = attemptsAllowed
        while (attemptsAllowed-- > 0 && successesCounter > 0) {
            if (lambda.invoke())
                successesCounter--
        }
        return successesCounter == 0
    }
}
