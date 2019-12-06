package aurocosh.divinefavor.common.item.base

import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext

interface ICastable {
    fun cast(context: CastContext): Boolean
}