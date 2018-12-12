package aurocosh.divinefavor.common.spell.base;

public enum SpellType {
    ARROW_THROW(1, "arrow_throw"),
    BONEMEAL(2, "bonemeal"),
    EMPOWER_AXE(3, "empower_axe"),
    EMPOWER_PICKAXE(14, "empower_pickaxe"),
    FELL_TREE(4, "fell_tree"),
    IGNITION(5, "ignition"),
    LAVAWALKING(6, "lavawalking"),
    SMALL_FIREBALL_THROW(7, "small_fireball_throw"),
    SNOWBALL_THROW(8, "snowball_throw"),
    STONE_FEVER(9, "stone_fever"),
    STONEBALL_THROW(10, "stoneball_throw"),
    TELL_TIME(11, "tell_time"),
    WATERWALKING(12, "waterwalking"),
    WOODEN_PUNCH(13, "wooden_punch"),
    CRUSHING_PALM(15, "crushing_palm");

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
