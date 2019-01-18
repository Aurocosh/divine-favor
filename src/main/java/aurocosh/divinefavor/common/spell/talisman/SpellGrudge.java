package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import aurocosh.divinefavor.common.player_data.grudge.IGrudgeHandler;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;

import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

public class SpellGrudge extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.grudge));
        IGrudgeHandler grudgeHandler = context.player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;
        new MessageSyncGrudge(grudgeHandler.getMobTypeId()).sendTo(context.player);
    }
}
