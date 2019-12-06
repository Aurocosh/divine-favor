package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import java.util.*

class SpellTalismanMinersFocus(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        context.player.addPotionEffect(ModEffect(ModPotions.miners_focus, ConfigSpell.minersFocus.hasteDuration))
        context.player.addPotionEffect(PotionEffect(MobEffects.HASTE, ConfigSpell.minersFocus.hasteDuration, ConfigSpell.minersFocus.hasteLevel))
    }
}
