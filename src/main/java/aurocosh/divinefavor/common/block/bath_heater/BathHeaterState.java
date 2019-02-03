package aurocosh.divinefavor.common.block.bath_heater;

import net.minecraft.util.IStringSerializable;

public enum BathHeaterState implements IStringSerializable {
    INACTIVE("inactive"),
    ACTIVE("active");

    // Optimization
    public static final BathHeaterState[] VALUES = BathHeaterState.values();

    private final String name;

    BathHeaterState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}