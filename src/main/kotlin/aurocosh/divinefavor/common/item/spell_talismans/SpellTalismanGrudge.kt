package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncGrudge
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*

class SpellTalismanGrudge(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        context.player.addPotionEffect(ModEffectToggle(ModPotions.grudge))

        val grudgeData = context.player.divinePlayerData.grudgeData
        MessageSyncGrudge(grudgeData.mobTypeId).sendTo(context.player)
    }
}
