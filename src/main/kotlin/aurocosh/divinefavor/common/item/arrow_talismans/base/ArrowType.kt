package aurocosh.divinefavor.common.item.arrow_talismans.base

import net.minecraft.util.math.MathHelper

enum class ArrowType(val value: Int) {
    WOODEN_ARROW(0),
    SPELL_ARROW(1),
    CURSED_ARROW(2);

    companion object {
        // Optimization
        val VALUES = values()

        operator fun get(value: Int): ArrowType {
            return VALUES[MathHelper.clamp(value, 0, VALUES.size - 1)]
        }
    }
}