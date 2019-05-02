package aurocosh.divinefavor.common.item.talismans.arrow.base

import java.util.*

enum class ArrowOptions {
    BreakOnHit,
    RequiresTarget;

    companion object {

        val NORMAL = EnumSet.noneOf(ArrowOptions::class.java)
        val ARROW_BREAKS = EnumSet.of(BreakOnHit)
        val REQUIRES_TARGET = EnumSet.of(RequiresTarget)
    }
}
