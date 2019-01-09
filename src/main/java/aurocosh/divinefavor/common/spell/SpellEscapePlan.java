package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.player_data.escape_plan.IEscapePlanHandler;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import static aurocosh.divinefavor.common.player_data.escape_plan.EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN;

public class SpellEscapePlan extends ModSpell {
    private final int DURATION = UtilTick.minutesToTicks(1);

    public SpellEscapePlan() {
        super("escape_plan");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        EntityPlayer player = context.player;
        IEscapePlanHandler planHandler = player.getCapability(CAPABILITY_ESCAPE_PLAN, null);
        assert planHandler != null;
        planHandler.setGlobalPosition(new GlobalBlockPos(context.player));

        player.addPotionEffect(new PotionEffect(ModPotions.escape_plan, DURATION));
    }
}
