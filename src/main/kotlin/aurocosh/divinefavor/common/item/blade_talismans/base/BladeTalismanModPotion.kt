package aurocosh.divinefavor.common.item.blade_talismans.base

import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.spirit.base.ModSpirit

class BladeTalismanModPotion : ItemBladeTalisman {
    private val duration: Int
    private val amplifier: Int
    private val potion: ModPotion

    constructor(name: String, spirit: ModSpirit, favorCost: Int, potion: ModPotion, duration: Int) : super(name, spirit, favorCost) {
        this.duration = duration
        this.amplifier = 0
        this.potion = potion
    }

    constructor(name: String, spirit: ModSpirit, favorCost: Int, potion: ModPotion, duration: Int, amplifier: Int) : super(name, spirit, favorCost) {
        this.duration = duration
        this.amplifier = amplifier
        this.potion = potion
    }

    override fun performActionServer(context: CastContext) {
        context.target?.addPotionEffect(ModEffect(potion, duration, amplifier))
    }
}
