package aurocosh.divinefavor.common.item.spell_talismans.base

import java.util.*

enum class SpellOptions {
    ItemUseCast,
    RightClickCast,
    OnRightCastRayTraceBlock,
    OnRightCastFindTargetEntity;

    companion object {
        val USE_CAST: EnumSet<SpellOptions> = EnumSet.of(ItemUseCast)
        val ALL_CAST: EnumSet<SpellOptions> = EnumSet.of(ItemUseCast, RightClickCast)
        val RIGHT_CAST: EnumSet<SpellOptions> = EnumSet.of(RightClickCast)
        val ENTITY_CAST: EnumSet<SpellOptions> = EnumSet.of(RightClickCast, OnRightCastFindTargetEntity)
        val ALL_CAST_TRACE: EnumSet<SpellOptions> = EnumSet.of(ItemUseCast, RightClickCast, OnRightCastRayTraceBlock)
        val TRACE_ONLY_CAST: EnumSet<SpellOptions> = EnumSet.of(RightClickCast, OnRightCastRayTraceBlock)
    }
}
