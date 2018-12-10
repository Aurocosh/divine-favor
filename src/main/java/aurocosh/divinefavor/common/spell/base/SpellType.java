package aurocosh.divinefavor.common.spell.base;

public enum SpellType {
    ARROW_THROW(1, "arrow_throw"),
    BONEMEAL(2, "bonemeal"),
    EMPOWER_AXE(3, "empower_axe"),
    FELL_TREE(4, "fell_tree"),
    IGNITION(5, "ignition"),
    LAVAWALKING(6, "lavawalking"),
    SNOWBALL_THROW(7, "snowball_throw"),
    STONEBALL_THROW(8, "stoneball_throw"),
    SMALL_FIREBALL_THROW(9, "small_fireball_throw"),
    TELL_TIME(10, "tell_time"),
    WATERWALKING(11, "waterwalking"),
    WOODEN_PUNCH(12, "wooden_punch");

    private final int id;
    private final String spellName;

    SpellType(int id, String spellName) {
        this.id = id;
        this.spellName = spellName;
    }

    public int getValue() {
        return id;
    }

    @Override
    public String toString() {
        return spellName;
    }
}
