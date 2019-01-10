package aurocosh.divinefavor.common.spell.base;

public abstract class ModSpell {
    public ModSpell() {
    }

    public boolean isConsumeCharge(SpellContext context) {
        return true;
    }

    public void cast(SpellContext context) {
        if (context.world.isRemote)
            performActionClient(context);
        else
            performActionServer(context);
    }

    protected void performActionServer(SpellContext context) {
    }

    protected void performActionClient(SpellContext context) {
    }
}