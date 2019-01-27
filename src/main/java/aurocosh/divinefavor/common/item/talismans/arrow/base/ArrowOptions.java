package aurocosh.divinefavor.common.item.talismans.arrow.base;

import java.util.EnumSet;

public enum ArrowOptions {
    BreakOnHit,
    RequiresTarget;

    public static final EnumSet<ArrowOptions> NORMAL = EnumSet.noneOf(ArrowOptions.class);
    public static final EnumSet<ArrowOptions> ARROW_BREAKS = EnumSet.of(ArrowOptions.BreakOnHit);
    public static final EnumSet<ArrowOptions> REQUIRES_TARGET = EnumSet.of(ArrowOptions.RequiresTarget);
}
