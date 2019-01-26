package aurocosh.divinefavor.common.entity.minions.base;

import net.minecraft.util.math.MathHelper;

public enum MinionMode {
    Normal(0),
    Wait(1);

    // Optimization
    public static final MinionMode[] VALUES = MinionMode.values();

    private final int value;

    MinionMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MinionMode fromInt(int mode) {
        mode = MathHelper.clamp(mode, 0, VALUES.length - 1);
        return VALUES[mode];
    }
}