package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.grudge.GrudgeData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

import java.util.EnumSet;

public class SpellTalismanGrudge extends ItemSpellTalisman {
    public SpellTalismanGrudge(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectToggle(ModPotions.grudge));

        GrudgeData grudgeData = PlayerData.get(context.player).getGrudgeData();
        new MessageSyncGrudge(grudgeData.getMobTypeId()).sendTo(context.player);
    }
}
