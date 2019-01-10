package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.util.text.TextComponentString;

public class SpellTellTime extends ModSpell {
    @Override
    protected void performActionClient(SpellContext context) {
        long time = context.world.getWorldTime();
        context.player.sendMessage(new TextComponentString("Time: " + time));
        context.player.sendMessage(new TextComponentString("Day time: " + time % 24000));
    }
}
