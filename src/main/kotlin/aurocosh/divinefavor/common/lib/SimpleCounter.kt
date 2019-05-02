package aurocosh.divinefavor.common.lib

open class SimpleCounter(private var required: Int) {
    var count: Int = 0

    val isFinished: Boolean
        get() = count >= required

    init {
        reset()
    }

    open fun setRequired(required: Int) {
        this.required = required
        reset()
    }

    fun reset() {
        count = 0
    }

    fun count(): Boolean {
        if (count >= required)
            return true
        count++
        return false
    }
}
