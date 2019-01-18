package aurocosh.divinefavor.common.util;

public enum  InventoryIndexes {
    HotBarStart(0),
    HotBarEnd(8),
    MainStart(9),
    MainEnd(35),
    CraftSlotsStart(36),
    CraftSlotsEnd(39),
    Offhand(40);

    // Optimization
    public static final InventoryIndexes[] VALUES = InventoryIndexes.values();

    private final int value;

    InventoryIndexes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}