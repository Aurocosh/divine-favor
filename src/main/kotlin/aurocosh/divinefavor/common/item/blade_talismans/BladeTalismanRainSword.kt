package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.util.DamageSource

class BladeTalismanRainSword(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: CastContext): Boolean {
        if (context.world.isRaining)
            return true
        if (context.target is EntityEnderman)
            return true
        return false
    }

    override fun performActionServer(context: CastContext) {
        context.target?.attackEntityFrom(DamageSource.DROWN, ConfigBlade.rainSword.damage)
    }
}
