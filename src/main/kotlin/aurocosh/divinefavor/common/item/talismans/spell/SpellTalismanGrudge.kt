package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*

class SpellTalismanGrudge(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        context.player.addPotionEffect(ModEffectToggle(ModPotions.grudge))

        val grudgeData = context.player.divineCustomData.grudgeData
        MessageSyncGrudge(grudgeData.mobTypeId).sendTo(context.player)
    }
}
