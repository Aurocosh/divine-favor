package aurocosh.divinefavor.common.block.medium;

import net.minecraft.util.IStringSerializable;

public enum MediumStone implements IStringSerializable {
    NONE("none"),
    ARBOW("arbow"),
    BLIZRABI("blizrabi"),
    ENDERERER("endererer"),
    LOON("loon"),
    NEBLAZE("neblaze"),
    REDWIND("redwind"),
    ROMOL("romol"),
    SQUAREFURY("squarefury"),
    TIMBER("timber");

    // Optimization
    public static final MediumStone[] VALUES = MediumStone.values();

    private final String name;

    MediumStone(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}