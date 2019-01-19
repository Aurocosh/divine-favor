package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.player_data.escape_plan.IEscapePlanHandler;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import static aurocosh.divinefavor.common.player_data.escape_plan.EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN;

public class TalismanEscapePlan extends ItemTalisman {
    private final int DURATION = UtilTick.minutesToTicks(1);
    private static final int USES = 10;

    public TalismanEscapePlan() {
        super("escape_plan", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        IEscapePlanHandler planHandler = player.getCapability(CAPABILITY_ESCAPE_PLAN, null);
        assert planHandler != null;
        planHandler.setGlobalPosition(new GlobalBlockPos(context.player));

        player.addPotionEffect(new PotionEffect(ModPotions.escape_plan, DURATION));
    }
}
