package aurocosh.divinefavor.common.lib

class SuccessCounter(private val successesNeeded: Int, private val attemptsMax: Int) {

    private var successes: Int = 0
    private var attempts: Int = 0

    init {
        reset()
    }

    fun reset() {
        successes = 0
        attempts = 0
    }

    fun attemptOnceMore(): Boolean {
        attempts++
        return if (successes >= successesNeeded) false else attempts < attemptsMax
    }

    fun registerSuccess() {
        successes++
    }

    fun registerSuccess(success: Boolean) {
        if (success)
            successes++
    }
}
