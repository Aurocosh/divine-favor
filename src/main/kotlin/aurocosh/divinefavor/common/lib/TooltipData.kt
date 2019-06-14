package aurocosh.divinefavor.common.lib

data class TooltipData(val langKey: String, val isLocal: Boolean, val x: Int, val y: Int) {

    fun getX(offeredX: Int): Int {
        return if (isLocal) x else offeredX
    }

    fun getY(offeredY: Int): Int {
        return if (isLocal) y else offeredY
    }
}