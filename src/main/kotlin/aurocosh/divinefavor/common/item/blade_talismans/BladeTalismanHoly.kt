package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource

class BladeTalismanHoly(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        val target = context.target ?: return false
        return target.isEntityUndead
    }

    override fun performActionServer(context: TalismanContext) {
        val target = context.target ?: return
        target.attackEntityFrom(DamageSource.MAGIC, ConfigBlade.holyBlade.damage)
        target.setFire(ConfigBlade.holyBlade.fireSeconds)
        target.addPotionEffect(PotionEffect(MobEffects.SLOWNESS, ConfigBlade.holyBlade.slownessTime, ConfigBlade.holyBlade.slownessPower))
    }
}
