package aurocosh.divinefavor.common.block.soulbound_lectern;

import net.minecraft.util.IStringSerializable;

public enum SoulboundLecternGem implements IStringSerializable {
    NONE("none"),
    END("end"),
    MIND("mind"),
    NETHER("nether"),
    PEACE("peace"),
    WILL("will"),
    UNDEATH("undeath"),
    WATER("water"),
    WILD("wild"),
    WITHER("wither");

    // Optimization
    public static final SoulboundLecternGem[] VALUES = SoulboundLecternGem.values();

    private final String name;

    SoulboundLecternGem(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}