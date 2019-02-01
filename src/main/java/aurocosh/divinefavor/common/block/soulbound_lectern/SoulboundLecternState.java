package aurocosh.divinefavor.common.block.soulbound_lectern;

import net.minecraft.util.IStringSerializable;

public enum SoulboundLecternState implements IStringSerializable {
    UNBOUND("unbound"),
    BOUND("bound");

    // Optimization
    public static final SoulboundLecternState[] VALUES = SoulboundLecternState.values();

    private final String name;

    SoulboundLecternState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}