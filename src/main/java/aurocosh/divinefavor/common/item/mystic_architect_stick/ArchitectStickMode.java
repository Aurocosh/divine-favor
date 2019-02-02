package aurocosh.divinefavor.common.item.mystic_architect_stick;

public enum ArchitectStickMode {
    SELECT_MULTIBLOCK_BASE(0, "Select multiblock base"),
    SELECT_CONTROLLER(1, "Select controller"),
    SELECT_AIR_BLOCK(2, "Select air block"),
    CREATE_TEMPLATE(3, "Create template");

    // Optimization
    public static final ArchitectStickMode[] VALUES = ArchitectStickMode.values();

    private final int value;

    private final String description;

    ArchitectStickMode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static int clampModeIndex(int mode) {
        if(mode > 3)
            mode = 0;
        if(mode < 0)
            mode = 3;
        return mode;
    }
}
