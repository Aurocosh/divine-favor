package aurocosh.divinefavor.common.item.spell_talismans.base

import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge
import aurocosh.divinefavor.common.spirit.base.ModSpirit

class SpellTalismanModPotionCharge(name: String, spirit: ModSpirit, favorCost: Int, private val potion: ModPotionCharge, private val charges: Int) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST) {

    override fun performActionServer(context: CastContext) {
        context.player.addPotionEffect(ModEffectCharge(potion, charges))
    }

    public override fun isConsumeCharge(context: CastContext): Boolean {
        return !context.player.isPotionActive(potion)
    }
}
