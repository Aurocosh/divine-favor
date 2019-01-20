package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import aurocosh.divinefavor.common.custom_data.player.grudge.IGrudgeHandler;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;

import static aurocosh.divinefavor.common.custom_data.player.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

public class SpellTalismanGrudge extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanGrudge() {
        super("grudge", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.grudge));
        IGrudgeHandler grudgeHandler = context.player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;
        new MessageSyncGrudge(grudgeHandler.getMobTypeId()).sendTo(context.player);
    }
}
