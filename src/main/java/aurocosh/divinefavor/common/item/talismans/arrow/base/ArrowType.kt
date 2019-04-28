package aurocosh.divinefavor.common.item.talismans.arrow.base

import aurocosh.divinefavor.common.util.UtilMath

enum class ArrowType private constructor(val value: Int) {
    WOODEN_ARROW(0),
    SPELL_ARROW(1),
    CURSED_ARROW(2);

    companion object {
        // Optimization
        val VALUES = values()

        operator fun get(value: Int): ArrowType {
            return VALUES[UtilMath.clamp(value, 0, VALUES.size - 1)]
        }
    }
}