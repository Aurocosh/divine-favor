package aurocosh.divinefavor.common.custom_data.living.data.curse

class CurseData {
    var curseCount: Int = 0

    val isCursed: Boolean
        get() = curseCount > 0

    fun addCurse() {
        curseCount++
    }

    fun removeCurse() {
        curseCount--
    }
}
