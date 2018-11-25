package aurocosh.divinefavor.common.spell.base;

public enum SpellChargeType {
    NOT_SET(0),
    SPELL_EMPOWER_AXE(1);

    private final int id;
    SpellChargeType(int id) { this.id = id; }
    public int getValue() { return id; }
}
