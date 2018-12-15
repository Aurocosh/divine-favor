package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.util.text.TextComponentString;

public class SpellTellTime extends ModSpell {
    public SpellTellTime() {
        super("tell_time");
    }

    @Override
    protected void performActionClient(SpellContext context) {
        long time = context.world.getWorldTime();
        context.player.sendMessage(new TextComponentString("Time: " + time));
    }
}
