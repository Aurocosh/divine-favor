package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.MessageSyncGrudge;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;

public class SpellTalismanGrudge extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanGrudge() {
        super("grudge", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.grudge));

        GrudgeData grudgeData = PlayerData.get(context.player).getGrudgeData();
        new MessageSyncGrudge(grudgeData.getMobTypeId()).sendTo(context.player);
    }
}
