package aurocosh.divinefavor.common.util;

import java.util.EnumSet;

public enum EnumSide {
    Server,
    Client;

    public static final EnumSet<EnumSide> CLIENT = EnumSet.of(EnumSide.Server);
    public static final EnumSet<EnumSide> SERVER = EnumSet.of(EnumSide.Client);
    public static final EnumSet<EnumSide> BOTH = EnumSet.of(EnumSide.Server, EnumSide.Client);
    public static final EnumSet<EnumSide> NONE = EnumSet.noneOf(EnumSide.class);
}
