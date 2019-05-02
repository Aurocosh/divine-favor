package aurocosh.divinefavor.common.item.talismans.spell.base

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.spirit.base.ModSpirit

class SpellTalismanModPotionToggle(name: String, spirit: ModSpirit, favorCost: Int, private val potion: ModPotionToggle) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST) {

    init {
        if (potion is ModPotionToggleLimited)
            potion.talisman = this
    }

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        if (player.isPotionActive(potion))
            player.removePotionEffect(potion)
        else
            player.addPotionEffect(ModEffectToggle(potion))
    }

    public override fun isConsumeCharge(context: TalismanContext): Boolean {
        return !context.player.isPotionActive(potion)
    }
}
