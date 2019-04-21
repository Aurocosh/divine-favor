package aurocosh.divinefavor.common.item.talismans.spell.base;

import java.util.EnumSet;

public enum SpellOptions {
    ItemUseCast,
    RightClickCast,
    OnRightCastRayTraceBlock,
    OnRightCastFindTargetEntity;

    public static final EnumSet<SpellOptions> USE_CAST = EnumSet.of(SpellOptions.ItemUseCast);
    public static final EnumSet<SpellOptions> ALL_CAST = EnumSet.of(SpellOptions.ItemUseCast, SpellOptions.RightClickCast);
    public static final EnumSet<SpellOptions> RIGHT_CAST = EnumSet.of(SpellOptions.RightClickCast);
    public static final EnumSet<SpellOptions> ENTITY_CAST = EnumSet.of(SpellOptions.RightClickCast, OnRightCastFindTargetEntity);
    public static final EnumSet<SpellOptions> ALL_CAST_TRACE = EnumSet.of(SpellOptions.ItemUseCast, SpellOptions.RightClickCast, SpellOptions.OnRightCastRayTraceBlock);
    public static final EnumSet<SpellOptions> TRACE_ONLY_CAST = EnumSet.of(SpellOptions.RightClickCast, SpellOptions.OnRightCastRayTraceBlock);
}
