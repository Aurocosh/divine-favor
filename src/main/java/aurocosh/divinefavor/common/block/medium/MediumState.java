package aurocosh.divinefavor.common.block.medium;

import net.minecraft.util.IStringSerializable;

public enum MediumState implements IStringSerializable {
    INVALID("invalid"),
    VALID("valid"),
    ACTIVE("active");

    // Optimization
    public static final MediumState[] VALUES = MediumState.values();

    private final String name;

    MediumState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}