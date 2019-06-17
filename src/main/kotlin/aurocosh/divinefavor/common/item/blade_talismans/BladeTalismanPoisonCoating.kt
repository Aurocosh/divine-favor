package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect

class BladeTalismanPoisonCoating(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: TalismanContext) {
        context.target?.addPotionEffect(PotionEffect(MobEffects.POISON, ConfigBlade.poisonCoating.poisonTime, ConfigBlade.poisonCoating.poisonPower))
    }
}
