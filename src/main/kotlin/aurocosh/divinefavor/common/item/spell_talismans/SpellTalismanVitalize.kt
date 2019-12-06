package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.MobEffects
import java.util.*

class SpellTalismanVitalize(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        context.player.removePotionEffect(MobEffects.WITHER)
        context.player.removePotionEffect(MobEffects.POISON)
    }
}
