package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import aurocosh.divinefavor.common.player_data.grudge.IGrudgeHandler;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;

import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

public class SpellGrudge extends ModSpell {

    public SpellGrudge() {
        super("grudge");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.grudge));
        IGrudgeHandler grudgeHandler = context.player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;
        MessageSyncGrudge.sync(context.player, grudgeHandler.getMobTypeId());
        return true;
    }
}
