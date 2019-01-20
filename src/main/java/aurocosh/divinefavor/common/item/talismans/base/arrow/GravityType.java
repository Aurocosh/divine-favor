package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.util.UtilMath;

public enum GravityType {
    NORMAL(0),
    NO_GRAVITY(1),
    ANTIGRAVITY(2);

    // Optimization
    public static final GravityType[] VALUES = GravityType.values();

    private final int value;

    GravityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GravityType get(int value){
        return VALUES[UtilMath.clamp(value, 0, VALUES.length -1 )];
    }
}