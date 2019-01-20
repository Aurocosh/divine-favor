package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.custom_data.player.escape_plan.IEscapePlanHandler;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import static aurocosh.divinefavor.common.custom_data.player.escape_plan.EscapePlanDataHandler.CAPABILITY_ESCAPE_PLAN;

public class SpellTalismanEscapePlan extends ItemSpellTalisman {
    private final int DURATION = UtilTick.minutesToTicks(1);
    private static final int USES = 10;

    public SpellTalismanEscapePlan() {
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
