package aurocosh.divinefavor.common.item.spell_talismans.base

import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.spirit.base.ModSpirit

class SpellTalismanModPotionToggle(name: String, spirit: ModSpirit, favorCost: Int, private val potion: ModPotionToggle, val isCastFree: Boolean = false, val amplifier: Int = 0) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST) {

    init {
        if (potion is ModPotionToggleLimited)
            potion.talisman = this
    }

    override fun performActionServer(context: CastContext) {
        val player = context.player
        if (player.isPotionActive(potion))
            player.removePotionEffect(potion)
        else
            player.addPotionEffect(ModEffectToggle(potion, amplifier))
    }

    public override fun isConsumeCharge(context: CastContext): Boolean {
        return !isCastFree && !context.player.isPotionActive(potion)
    }
}
