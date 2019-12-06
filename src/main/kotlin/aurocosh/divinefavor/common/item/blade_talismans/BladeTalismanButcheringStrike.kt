package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.util.DamageSource

class BladeTalismanButcheringStrike(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: CastContext): Boolean {
        val target = context.target ?: return false
        if (target !is IAnimals)
            return false
        if (target is IMob)
            return false
        return true
    }

    override fun performActionServer(context: CastContext) {
        val target = context.target ?: return
        target.divineLivingData.extraLootingData.extraLooting += ConfigBlade.butcheringStrike.extraLooting
        target.attackEntityFrom(DamageSource.causePlayerDamage(context.player), ConfigBlade.butcheringStrike.extraDamage)
    }
}
