package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import java.util.*

class SpellTalismanMinersFocus(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        context.player.addPotionEffect(ModEffect(ModPotions.miners_focus, ConfigSpells.minersFocus.hasteDuration))
        context.player.addPotionEffect(PotionEffect(MobEffects.HASTE, ConfigSpells.minersFocus.hasteDuration, ConfigSpells.minersFocus.hasteLevel))
    }
}
