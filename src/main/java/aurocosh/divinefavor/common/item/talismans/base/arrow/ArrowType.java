package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.util.UtilMath;

public enum  ArrowType {
    WOODEN_ARROW(0),
    SPELL_ARROW(1),
    CURSED_ARROW(2);

    // Optimization
    public static final ArrowType[] VALUES = ArrowType.values();

    private final int value;

    ArrowType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ArrowType get(int value){
        return VALUES[UtilMath.clamp(value, 0, VALUES.length -1 )];
    }
}